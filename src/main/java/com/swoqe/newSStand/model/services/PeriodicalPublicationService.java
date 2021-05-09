package com.swoqe.newSStand.model.services;

import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.util.DBCPDataSource;
import com.swoqe.newSStand.util.ImageProcessing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalPublicationService {
    final static Logger logger = LogManager.getLogger(PeriodicalPublicationService.class);

    private final String ADD_PUBLICATION = "INSERT INTO periodical_publications " +
            "(name, publication_date, cover_img, publisher, img_name) " +
            "values (?, ?, ?, ?, ?)";

    private final String GET_N_PUBLICATIONS = "select periodical_publications.*, pp.price as price, p.name as period_name " +
            "from periodical_publications \n" +
            "join publication_periods pp on periodical_publications.id = pp.publication_id \n" +
            "join periods p on pp.period_id = p.id \n" +
            "limit ?";

    // TO DO REDO!
    public void addPublication(PeriodicalPublication publication){
        try(Connection connection = DBCPDataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(ADD_PUBLICATION)){

            ps.setString(1, publication.getName());
            ps.setDate(2, Date.valueOf(publication.getPublicationDate()));

            File img = publication.getCoverImg();
            try(FileInputStream fileInputStream = new FileInputStream(img)){
                ps.setBinaryStream(3, fileInputStream, img.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ps.setString(4, publication.getPublisher());
            ps.setString(5, publication.getImageName());

            ps.executeUpdate();
        } catch (SQLException e){
            logger.error(e);
        }
    }

    public List<PeriodicalPublication> getNPublications(int n) {
        List<PeriodicalPublication> publications = new ArrayList<>();

        try(Connection connection = DBCPDataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_N_PUBLICATIONS)){

            ps.setInt(1, n);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    PeriodicalPublication p = new PeriodicalPublication();
                    p.setId(resultSet.getLong("id"));
                    p.setName(resultSet.getString("name"));
                    p.setPublicationDate(resultSet.getDate("publication_date").toLocalDate());
                    p.setCoverImg(ImageProcessing.bytesToImage(resultSet.getBytes("cover_img"),
                            resultSet.getString("img_name")));
                    p.setPublisher(resultSet.getString("publisher"));
                    p.setPeriodName(resultSet.getString("period_name"));
                    p.setShownLowestPrice(resultSet.getBigDecimal("price"));
                    p.setDescription(resultSet.getString("description"));

                    publications.add(p);
                }
            }
        } catch (SQLException e){
            logger.error(e);
        }

        return publications;
    }


}
