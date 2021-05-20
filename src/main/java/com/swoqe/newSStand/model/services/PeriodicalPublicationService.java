package com.swoqe.newSStand.model.services;

import com.swoqe.newSStand.model.entity.Genre;
import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PeriodicalPublicationService {
    final static Logger logger = LogManager.getLogger(PeriodicalPublicationService.class);

    private final PeriodService periodService = new PeriodService();
    private final GenreService genreService = new GenreService();

    private static final String UPLOAD_DIRECTORY = "layouts/static/pp_covers";

    private final String ADD_PUBLICATION = "INSERT INTO periodical_publications " +
            "(id, name, publication_date, cover_img, publisher, img_name, description) " +
            "values (DEFAULT, ?, ?, ?, ?, ?, ?)";

    private final String GET_N_PUBLICATIONS_BY_GENRES = "SELECT id, name, publication_date, cover_img, publisher, img_name, description, count(*) OVER() AS total_count " +
            "from periodical_publications " +
            "where id in (select publication_id from publication_genre where genre_id=ANY(?)) order by %s %s limit ? offset ?";

    private final String GET_N_PUBLICATIONS = "SELECT id, name, publication_date, cover_img, publisher, img_name, description, count(*) OVER() AS total_count " +
            "from periodical_publications order by %s %s limit ? offset ?";


    public void addPublication(PeriodicalPublication publication){
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(ADD_PUBLICATION, Statement.RETURN_GENERATED_KEYS)
        ){
            ps.setString(1, publication.getName());
            ps.setDate(2, Date.valueOf(publication.getPublicationDate()));
            ps.setString(4, publication.getPublisher());
            ps.setString(6, publication.getDescription());

            Optional<File> optionalFile = Optional.ofNullable(publication.getCoverImg());
            if(optionalFile.isPresent()){
                File img = optionalFile.get();
                try(FileInputStream fileInputStream = new FileInputStream(img)){
                    ps.setBinaryStream(3, fileInputStream, (int) img.length());
                    ps.setString(5, img.getName());
                    ps.executeUpdate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(5, Types.VARCHAR);
                ps.executeUpdate();
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    publication.setId(generatedKeys.getLong("id"));
                }
            }
            this.periodService.insertPublicationPeriods(publication);
            this.genreService.insertPublicationGenres(publication);
            logger.info("DB | Publication with name '{}' was added.", publication.getName());
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
    }

    public PublicationsWrapper getPublicationsOrderedBy(String realPath, FilterConfiguration configuration) {
        int recordsPerPage = configuration.getRecordsPerPage();
        int pageNumber = configuration.getPageNumber();
        OrderBy orderBy = configuration.getOrderBy();
        SortingDirection direction = configuration.getSortingDirection();
        List<PeriodicalPublication> publications = new ArrayList<>();
        int totalAmount = 0;
        int start = pageNumber * recordsPerPage - recordsPerPage;
        String strOrderedBy = (orderBy.equals(OrderBy.PRICE) ? OrderBy.NAME.toString().toLowerCase() : orderBy.toString().toLowerCase());
        String finalQuery = String.format(GET_N_PUBLICATIONS, strOrderedBy, direction.toString());
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(finalQuery)
        ){
            ps.setInt(1, recordsPerPage);
            ps.setInt(2, start);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    byte[] bytes = resultSet.getBytes("cover_img");
                    String fileName = resultSet.getString("img_name");
                    File file = null;
                    if(bytes != null){
                        file = ImageProcessing.bytesToFile(bytes, fileName, UPLOAD_DIRECTORY, realPath);
                    }
                    totalAmount = resultSet.getInt("total_count");
                    Long id = resultSet.getLong("id");
                    Map<String, BigDecimal> prices = periodService.getPeriodsByPublicationId(id);
                    List<Genre> genres = genreService.getGenresByPublicationId(id);
                    PeriodicalPublication p = new PeriodicalPublication.PublicationBuilder()
                            .withId(id)
                            .withName(resultSet.getString("name"))
                            .withPublicationDate(resultSet.getDate("publication_date").toLocalDate())
                            .withCoverImg(file)
                            .withPublisher(resultSet.getString("publisher"))
                            .withPricesMap(prices)
                            .withGenres(genres)
                            .withDescription(resultSet.getString("description"))
                            .build();
                    publications.add(p);
                }
            }
            logger.info("DB | All publications request.");
        } catch (SQLException e){
            logger.error(e);
        }
        if (orderBy.equals(OrderBy.PRICE))
            publications.sort(Comparator.comparing(PeriodicalPublication::getShownPrice));
        return new PublicationsWrapper(publications, totalAmount);
    }

    public PublicationsWrapper getPublicationsByGenresOrderedBy(String realPath, FilterConfiguration configuration){
        List<PeriodicalPublication> publications = new ArrayList<>();
        int totalAmount = 0;
        int recordsPerPage = configuration.getRecordsPerPage();
        int pageNumber = configuration.getPageNumber();
        OrderBy orderBy = configuration.getOrderBy();
        Integer[] genresIds = configuration.getGenresIds();
        SortingDirection direction = configuration.getSortingDirection();
        int start = pageNumber * recordsPerPage - recordsPerPage;
        String strOrderedBy = (orderBy.equals(OrderBy.PRICE) ? OrderBy.NAME.toString().toLowerCase() : orderBy.toString().toLowerCase());
        String finalQuery = String.format(GET_N_PUBLICATIONS_BY_GENRES, strOrderedBy, direction.toString());
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(finalQuery)
        ){
            ps.setArray(1, connection.createArrayOf("int", genresIds));
            ps.setInt(2, recordsPerPage);
            ps.setInt(3, start);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    byte[] bytes = resultSet.getBytes("cover_img");
                    String fileName = resultSet.getString("img_name");
                    File file = null;
                    if(bytes != null){
                        file = ImageProcessing.bytesToFile(bytes, fileName, UPLOAD_DIRECTORY, realPath);
                    }
                    totalAmount = resultSet.getInt("total_count");
                    Long id = resultSet.getLong("id");
                    Map<String, BigDecimal> prices = periodService.getPeriodsByPublicationId(id);
                    List<Genre> genres = genreService.getGenresByPublicationId(id);
                    PeriodicalPublication p = new PeriodicalPublication.PublicationBuilder()
                            .withId(id)
                            .withName(resultSet.getString("name"))
                            .withPublicationDate(resultSet.getDate("publication_date").toLocalDate())
                            .withCoverImg(file)
                            .withPublisher(resultSet.getString("publisher"))
                            .withPricesMap(prices)
                            .withGenres(genres)
                            .withDescription(resultSet.getString("description"))
                            .build();
                    publications.add(p);
                }
            }
            logger.info("DB | All publications request.");
        } catch (SQLException e){
            logger.error(e);
        }
        return new PublicationsWrapper(publications, totalAmount);
    }

    public static class PublicationsWrapper{
        private List<PeriodicalPublication> publications;
        private Integer totalAmount;

        public PublicationsWrapper(List<PeriodicalPublication> publications, Integer totalAmount) {
            this.publications = publications;
            this.totalAmount = totalAmount;
        }

        public List<PeriodicalPublication> getPublications() {
            return publications;
        }

        public void setPublications(List<PeriodicalPublication> publications) {
            this.publications = publications;
        }

        public Integer getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
        }
    }
}
