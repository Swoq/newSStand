package com.swoqe.newSStand.model.services;

import com.swoqe.newSStand.model.entity.Genre;
import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.util.DBCPDataSource;
import com.swoqe.newSStand.util.ImageProcessing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PeriodicalPublicationService {
    final static Logger logger = LogManager.getLogger(PeriodicalPublicationService.class);

    private final PeriodService periodService = new PeriodService();
    private final GenreService genreService = new GenreService();

    private static final String UPLOAD_DIRECTORY = "layouts/static/pp_covers";

    private final String ADD_PUBLICATION = "INSERT INTO periodical_publications " +
            "(id, name, publication_date, cover_img, publisher, img_name, description) " +
            "values (DEFAULT, ?, ?, ?, ?, ?, ?)";

    private final String GET_N_PUBLICATIONS = "SELECT id, name, publication_date, cover_img, publisher, img_name, description " +
            "from periodical_publications limit ?";

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

    public List<PeriodicalPublication> getNPublications(int n, String realPath) {
        List<PeriodicalPublication> publications = new ArrayList<>();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_N_PUBLICATIONS)
        ){
            ps.setInt(1, n);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    byte[] bytes = resultSet.getBytes("cover_img");
                    String fileName = resultSet.getString("img_name");
                    File file = null;
                    if(bytes != null){
                        file = ImageProcessing.bytesToFile(bytes, fileName, UPLOAD_DIRECTORY, realPath);
                    }
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
        return publications;
    }


}
