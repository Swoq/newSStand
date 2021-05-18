package com.swoqe.newSStand.model.services;

import com.swoqe.newSStand.model.entity.Genre;
import com.swoqe.newSStand.model.entity.Period;
import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.util.DBCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GenreService {
    final static Logger logger = LogManager.getLogger(GenreService.class);

    private final String GET_ALL_GENRES = "select id, name, description from genres";
    private final String ADD_NEW_GENRE = "insert into genres(id, name, description) values(default, ?, ?)";
    private final String GET_GENRES_BY_PUBLICATION_ID = "select g.name, g.description, g.id from publication_genre p join genres g on g.id = p.genre_id where p.publication_id=?";
    private final String GET_GENRES_BY_NAMES = "select id, name, description from genres where name=ANY (?)";
    private final String INSERT_PUBLICATION_GENRES = "insert into publication_genre(id, publication_id, genre_id) values(default, ?, ?)";

    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_ALL_GENRES)
        ){
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Genre genre = new Genre(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    );
                    genres.add(genre);
                }
            }
            logger.info("DB | All Genres request.");
        }catch (SQLException e){
            logger.error(e);
        }

        return genres;
    }

    public void addNewGenre(Genre genre){
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(ADD_NEW_GENRE)
        ){
            ps.setString(1, genre.getName());
            ps.setString(2, genre.getDescription());
            ps.executeUpdate();
            logger.info("DB | Genre was added: {}", genre);
        } catch (SQLException e){
            logger.error(e);
        }
    }

    public List<Genre> getGenresByPublicationId(Long id) {
        List<Genre> genres = new ArrayList<>();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_GENRES_BY_PUBLICATION_ID)
        ){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Genre genre = new Genre(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    );
                    genres.add(genre);
                }
            }
            logger.info("DB | Genres '{}' were found for publication id='{}'", genres, id);
        }catch (SQLException e){
            logger.error(e);
        }
        return genres;
    }

    public List<Genre> getGenresByNames(String[] genresStrings) {
        List<Genre> genres = new ArrayList<>();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_GENRES_BY_NAMES)
        ){
            ps.setArray(1, connection.createArrayOf("text", genresStrings));
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Genre genre = new Genre(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    );
                    genres.add(genre);
                }
            }
            logger.info("DB | Genres with names:'{}' were found.", genres);
        }catch (SQLException e){
            logger.error(e);
        }
        return genres;
    }

    public void insertPublicationGenres(PeriodicalPublication publication) {
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_PUBLICATION_GENRES)
        ){
            List<Genre> genres = publication.getGenres();
            genres.forEach((genre) -> {
                try {
                    ps.setLong(1, publication.getId());
                    ps.setInt(2, genre.getId());
                    ps.addBatch();
                } catch (SQLException e) {
                    logger.error(e);
                }
            });

            ps.executeBatch();
            logger.info("DB | Genres '{}' were bind to the publication with id '{}'.", publication.getGenres(), publication.getId());
        }catch (SQLException e){
            logger.error(e);
        }
    }


}
