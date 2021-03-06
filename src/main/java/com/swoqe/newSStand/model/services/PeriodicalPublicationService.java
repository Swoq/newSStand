package com.swoqe.newSStand.model.services;

import com.swoqe.newSStand.model.entity.Genre;
import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.model.entity.Rate;
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

    private final String UPDATE_PUBLICATION = "UPDATE periodical_publications " +
            "SET name=?, publication_date=?, cover_img=?, publisher=?, img_name=?, description=? " +
            "WHERE id=? ";

    private final String GET_PUBLICATIONS_BY_NAME = "select publications.*, t.price, p.name as period_name, count(*) OVER() AS total_count\n" +
            "from publication_periods pp\n" +
            "join (\n" +
            "    select publication_id, min(price) as price\n" +
            "    from publication_periods\n" +
            "    group by publication_id\n" +
            ") t on pp.publication_id = t.publication_id and pp.price = t.price\n" +
            "join periods p on p.id = pp.period_id\n" +
            "join periodical_publications publications on publications.id = pp.publication_id\n" +
            "where publications.name ilike ? ESCAPE '!' limit ? offset ? ";

    private final String GET_N_PUBLICATIONS_BY_GENRES = "select publications.*, t.price, p.name as period_name, count(*) OVER() AS total_count \n" +
            "from publication_periods pp\n" +
            "join (\n" +
            "    select publication_id, min(price) as price\n" +
            "    from publication_periods\n" +
            "    group by publication_id\n" +
            ") t on pp.publication_id = t.publication_id and pp.price = t.price\n" +
            "join periods p on p.id = pp.period_id\n" +
            "join periodical_publications publications on publications.id = pp.publication_id\n" +
            "where t.publication_id in (select publication_id from publication_genre where genre_id=ANY(?))\n" +
            "order by %s %s \n" +
            "limit ? offset ? ";

    private final String GET_N_PUBLICATIONS = "select publications.*, t.price, p.name as period_name, count(*) OVER() AS total_count \n" +
            "from publication_periods pp\n" +
            "join (\n" +
            "    select publication_id, min(price) as price\n" +
            "    from publication_periods\n" +
            "    group by publication_id\n" +
            ") t on pp.publication_id = t.publication_id and pp.price = t.price\n" +
            "join periods p on p.id = pp.period_id\n" +
            "join periodical_publications publications on publications.id = pp.publication_id\n" +
            "order by %s %s \n" +
            "limit ? offset ? ";

    private final String GET_PUBLICATION_BY_ID = "select id, name, publication_date, " +
            "cover_img, publisher, img_name, description, 1 AS total_count, '' as period_name, cast('0' as money) as price " +
            "from periodical_publications where id=? ";


    public void addPublication(PeriodicalPublication publication){
        try(
                Connection connection = ConnectionPool.getInstance().getConnection();
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
                    logger.error(e);
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
        } catch (SQLException e){
            logger.error(e);
        }
    }

    public PublicationsWrapper getPublicationsOrderedBy(String realPath, FilterConfiguration configuration) {
        int recordsPerPage = configuration.getRecordsPerPage();
        int pageNumber = configuration.getPageNumber();
        OrderBy orderBy = configuration.getOrderBy();
        SortingDirection direction = configuration.getSortingDirection();
        PublicationsWrapper wrapper = new PublicationsWrapper();
        int start = pageNumber * recordsPerPage - recordsPerPage;

        String query = String.format(GET_N_PUBLICATIONS, orderBy.toString().toLowerCase(), direction.toString().toLowerCase());
        try(
                Connection connection = ConnectionPool.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ){
            ps.setInt(1, recordsPerPage);
            ps.setInt(2, start);
            wrapper = this.receivePublications(ps, realPath);

        } catch (SQLException e){
            logger.error(e);
        }
        return wrapper;
    }

    public PublicationsWrapper getPublicationsByGenresOrderedBy(String realPath, FilterConfiguration configuration){
        PublicationsWrapper wrapper = new PublicationsWrapper();
        int recordsPerPage = configuration.getRecordsPerPage();
        int pageNumber = configuration.getPageNumber();
        OrderBy orderBy = configuration.getOrderBy();
        Integer[] genresIds = configuration.getGenresIds();
        SortingDirection direction = configuration.getSortingDirection();
        int start = pageNumber * recordsPerPage - recordsPerPage;
        String finalQuery = String.format(GET_N_PUBLICATIONS_BY_GENRES, orderBy.toString().toLowerCase(), direction.toString().toLowerCase());
        try(
                Connection connection = ConnectionPool.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(finalQuery)
        ){
            ps.setArray(1, connection.createArrayOf("int", genresIds));
            ps.setInt(2, recordsPerPage);
            ps.setInt(3, start);
            wrapper = this.receivePublications(ps, realPath);

        } catch (SQLException e){
            logger.error(e);
        }
        return wrapper;
    }

    public PublicationsWrapper getPublicationsByName(String name, String realPath, FilterConfiguration configuration){
        int recordsPerPage = configuration.getRecordsPerPage();
        int pageNumber = configuration.getPageNumber();
        PublicationsWrapper wrapper = new PublicationsWrapper();
        int start = pageNumber * recordsPerPage - recordsPerPage;
        try(
                Connection connection = ConnectionPool.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_PUBLICATIONS_BY_NAME)
        ){
            name = name
                    .replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");

            ps.setString(1, "%" + name + "%");
            ps.setInt(2, recordsPerPage);
            ps.setInt(3, start);
            wrapper = this.receivePublications(ps, realPath);

        } catch (SQLException e){
            logger.error(e);
        }

        return wrapper;
    }

    public Optional<PeriodicalPublication> getPublicationById(Long id, String realPath) {
        try(
                Connection connection = ConnectionPool.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_PUBLICATION_BY_ID)
        ){
            ps.setLong(1, id);
            PublicationsWrapper wrapper = receivePublications(ps, realPath);
            if(wrapper.getPublications().size() == 0)
                return Optional.empty();
            else
                return Optional.of(wrapper.getPublications().get(0));

        } catch (SQLException e){
            logger.error(e);
        }

        return Optional.empty();
    }

    private PublicationsWrapper receivePublications(PreparedStatement ps, String realPath){
        List<PeriodicalPublication> publications = new ArrayList<>();
        int totalAmount = 0;
        try(ResultSet resultSet = ps.executeQuery()){
            while(resultSet.next()){
                byte[] bytes = resultSet.getBytes("cover_img");
                String fileName = resultSet.getString("img_name");
                File file;
                if(bytes != null){
                    file = ImageProcessing.bytesToFile(bytes, fileName, UPLOAD_DIRECTORY, realPath);
                }
                else
                    file = new File(realPath + File.separator + UPLOAD_DIRECTORY + File.separator + "default.svg");
                totalAmount = resultSet.getInt("total_count");
                Long id = resultSet.getLong("id");
                Map<String, BigDecimal> prices = periodService.getPeriodsMapByPublicationId(id);
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
                        .withShownPeriod(resultSet.getString("period_name"))
                        .withShownPrice(resultSet.getBigDecimal("price"))
                        .build();
                publications.add(p);
            }
        } catch (SQLException e){
            logger.error(e);
        }
        return new PublicationsWrapper(publications, totalAmount);
    }

    public void updatePublication(PeriodicalPublication publication) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try(
                PreparedStatement ps = connection.prepareStatement(UPDATE_PUBLICATION)
        ){
            connection.setAutoCommit(false);

            ps.setString(1, publication.getName());
            ps.setDate(2, Date.valueOf(publication.getPublicationDate()));
            ps.setString(4, publication.getPublisher());
            ps.setString(6, publication.getDescription());
            ps.setLong(7, publication.getId());

            Optional<File> optionalFile = Optional.ofNullable(publication.getCoverImg());
            if(optionalFile.isPresent()){
                File img = optionalFile.get();
                try(FileInputStream fileInputStream = new FileInputStream(img)){
                    ps.setBinaryStream(3, fileInputStream, (int) img.length());
                    ps.setString(5, img.getName());
                    ps.executeUpdate();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
            else{
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(5, Types.VARCHAR);
                ps.executeUpdate();
            }
//            TO DO Fix duplicated
            List<Rate> rates = this.periodService.getRatesByPublicationId(publication.getId());
            List<String> toRemove = new ArrayList<>();
            for(Map.Entry<String, BigDecimal> entry : publication.getPricesPerPeriods().entrySet()){
                for (Rate rate : rates){
                    if (entry.getKey().equals(rate.getPeriod().getName()))
                        toRemove.add(entry.getKey());
                }
            }
            for (String str : toRemove)
                publication.getPricesPerPeriods().remove(str);

            this.genreService.deletePublicationGenres(publication);

            this.periodService.insertPublicationPeriods(publication);
            this.genreService.insertPublicationGenres(publication);
            connection.commit();
        } catch (SQLException e){
            try {
                logger.error("Transaction is being rolled back");
                connection.rollback();
                connection.close();
            } catch (SQLException excep) {
                logger.error(excep);
            }
            logger.error(e);
        }
    }


    public static class PublicationsWrapper{
        private List<PeriodicalPublication> publications;
        private Integer totalAmount;

        public PublicationsWrapper(List<PeriodicalPublication> publications, Integer totalAmount) {
            this.publications = publications;
            this.totalAmount = totalAmount;
        }

        public PublicationsWrapper() {

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
