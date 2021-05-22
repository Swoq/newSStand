package com.swoqe.newSStand.model.services;

import com.swoqe.newSStand.model.entity.Period;
import com.swoqe.newSStand.model.entity.Rate;
import com.swoqe.newSStand.model.entity.Subscription;
import com.swoqe.newSStand.model.entity.User;
import com.swoqe.newSStand.util.DBCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionService {
    final static Logger logger = LogManager.getLogger(SubscriptionService.class);

    private final String GET_SUBSCRIPTIONS_BY_USER_ID = "select up.id as sub_id, publication.name as publ_name, " +
            "publication.id as publ_id, " +
            "p.name as period_name, p.id as period_id, " +
            "p.description as period_description, pp.price as sub_price, up.start_date, up.end_date \n" +
            "from user_publications up \n" +
            "join users u on u.id = up.user_id \n" +
            "join publication_periods pp on up.rate_id = pp.id \n" +
            "join periodical_publications publication on pp.publication_id = publication.id \n" +
            "join periods p on p.id = pp.period_id \n" +
            "where user_id=? ";

    private final String GET_SUBSCRIPTION_BY_USER_ID_AND_RATE_ID = "select up.id as sub_id, publication.name as publ_name, " +
            "publication.id as publ_id, " +
            "p.name as period_name, p.id as period_id, " +
            "p.description as period_description, pp.price as sub_price, up.start_date, up.end_date \n" +
            "from user_publications up \n" +
            "join users u on u.id = up.user_id \n" +
            "join publication_periods pp on up.rate_id = pp.id \n" +
            "join periodical_publications publication on pp.publication_id = publication.id \n" +
            "join periods p on p.id = pp.period_id \n" +
            "where user_id=? and rate_id=? limit 1";

    public List<Subscription> getSubscriptionsByUser(User user) {
        List<Subscription> subscriptions = new ArrayList<>();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_SUBSCRIPTIONS_BY_USER_ID)
        ){
            ps.setLong(1, user.getId());
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Period period = new Period();
                    period.setId(rs.getInt("period_id"));
                    period.setName(rs.getString("period_name"));
                    period.setDescription(rs.getString("period_description"));
                    Subscription subscription = new Subscription(
                            rs.getLong("sub_id"),
                            user,
                            rs.getString("publ_name"),
                            rs.getLong("publ_id"),
                            period,
                            rs.getBigDecimal("sub_price"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate()
                    );
                    subscriptions.add(subscription);
                }
            }
        }catch (SQLException e){
            logger.error(e);
        }
        return subscriptions;
    }

    public Optional<Subscription> getSubscriptionByUserIdAndRateId(User user, Long rateId) {
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_SUBSCRIPTION_BY_USER_ID_AND_RATE_ID)
        ){
            ps.setLong(1, user.getId());
            ps.setLong(2, rateId);
            try(ResultSet rs = ps.executeQuery()){
                rs.next();
                Period period = new Period();
                period.setId(rs.getInt("period_id"));
                period.setName(rs.getString("period_name"));
                period.setDescription(rs.getString("period_description"));
                Subscription subscription = new Subscription(
                        rs.getLong("sub_id"),
                        user,
                        rs.getString("publ_name"),
                        rs.getLong("publ_id"),
                        period,
                        rs.getBigDecimal("sub_price"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                );
                return Optional.of(subscription);
            }
        }catch (SQLException e){
            logger.error(e);
        }
        return Optional.empty();
    }

    public void doUserSubscribe(Long userId, Long rateId){
        try(
                Connection connection = DBCPDataSource.getConnection();
                CallableStatement cs = connection.prepareCall("CALL do_user_subscription(?, ?)");
        ){
            cs.setLong(1, userId);
            cs.setLong(2, rateId);

            cs.executeUpdate();
            logger.info("Subscription {} was added to {}", rateId, userId);
        }
        catch (SQLException e){
            logger.error(e);
        }
    }


}
