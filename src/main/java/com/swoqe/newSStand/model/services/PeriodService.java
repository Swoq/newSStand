package com.swoqe.newSStand.model.services;

import com.swoqe.newSStand.model.entity.Period;
import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.model.entity.Rate;
import com.swoqe.newSStand.util.DBCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PeriodService {
    final static Logger logger = LogManager.getLogger(PeriodService.class);

    private final String GET_ALL_PERIODS = "select id, name, description from periods";

    private final String ADD_NEW_PERIOD = "insert into periods(name, description) VALUES (?, ?)";

    private final String ADD_PERIOD_BY_ID = "insert into publication_periods(id, period_id, publication_id, price) " +
            "values (default, ?, ?, ?)";

    private final String GET_PERIOD_BY_NAME = "select id, name, description from periods where name=?";

    private final String GET_PERIOD_BY_ID = "select id, name, description from periods where id=?";

    private final String GET_PERIODS_ID_BY_PUBLICATION_ID = "select period_id, price from publication_periods " +
            "where publication_id=?";

    private final String GET_RATES_BY_PUBLICATION_ID = "select p.id as period_id, p.name, p.description, pp.price, pp.id " +
            "from publication_periods pp \n" +
            "join periods p on p.id = pp.period_id \n" +
            "where publication_id=? order by price ";

    private final String GET_RATES_BY_ID = "select p.id as period_id, p.name, p.description, pp.price, pp.id " +
            "from publication_periods pp \n" +
            "join periods p on p.id = pp.period_id \n" +
            "where pp.id=? ";

    public List<Period> getAllPeriods(){
        List<Period> periods = new ArrayList<>();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_ALL_PERIODS)
        ){
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Period period = new Period(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    );
                    periods.add(period);
                }
            }
        }catch (SQLException e){
            logger.error(e);
        }
        return periods;
    }

    public void addNewPeriod(Period newPeriod){
        try(Connection connection = DBCPDataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(ADD_NEW_PERIOD)){

            ps.setString(1, newPeriod.getName());
            ps.setString(2, newPeriod.getDescription());

            ps.executeUpdate();
        } catch (SQLException e){
            logger.error(e);
        }
    }

    public void insertPublicationPeriods(PeriodicalPublication publication){
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(ADD_PERIOD_BY_ID)
        ){
            Map<String, BigDecimal> periodsPrices = publication.getPricesPerPeriods();
            periodsPrices.forEach((k, v) -> {
                Optional<Period> period = this.getPeriodByName(k);
                if(period.isPresent()){
                    try {
                        ps.setInt(1, period.get().getId());
                        ps.setLong(2, publication.getId());
                        ps.setBigDecimal(3, v);
                        ps.addBatch();
                    } catch (SQLException e) {
                        logger.error(e);
                    }
                }
            });
            ps.executeBatch();
        }catch (SQLException e){
            logger.error(e);
        }
    }

    public Map<String, BigDecimal> getPeriodsMapByPublicationId(Long id){
        Map<String, BigDecimal> map = new HashMap<>();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_PERIODS_ID_BY_PUBLICATION_ID)
        ){
            ps.setLong(1, id);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    int periodId = resultSet.getInt("period_id");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    Optional<Period> optionalPeriod = this.getPeriodById(periodId);
                    if (optionalPeriod.isPresent()) {
                        String periodName = optionalPeriod.get().getName();
                        map.put(periodName, price);
                    }
                }
            }
        }catch (SQLException e){
            logger.error(e);
        }
        return map;
    }

    public List<Rate> getRatesByPublicationId(Long id){
        List<Rate> rates = new ArrayList<>();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_RATES_BY_PUBLICATION_ID)
        ){
            ps.setLong(1, id);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    Long rateId = resultSet.getLong("id");
                    int periodId = resultSet.getInt("period_id");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Period period = new Period(periodId, name, description);
                    Rate rate = new Rate(rateId, period, id, price);
                    rates.add(rate);
                }
            }
        }catch (SQLException e){
            logger.error(e);
        }
        return rates;
    }

    public Optional<Rate> getRateById(Long id){
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_RATES_BY_ID);
        ){
            ps.setLong(1, id);
            try (ResultSet resultSet = ps.executeQuery()){
                resultSet.next();

                Long rateId = resultSet.getLong("id");
                int periodId = resultSet.getInt("period_id");
                BigDecimal price = resultSet.getBigDecimal("price");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Period period = new Period(periodId, name, description);
                Rate rate = new Rate(rateId, period, id, price);
                return Optional.of(rate);
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        return Optional.empty();
    }

    public Optional<Period> getPeriodByName(String name){
        Optional<Period> optionalPeriod = Optional.empty();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_PERIOD_BY_NAME)
        ){
            ps.setString(1, name);
            optionalPeriod = getOptionalPeriod(ps);
        }catch (SQLException e){
            logger.error(e);
        }
        return optionalPeriod;
    }

    public Optional<Period> getPeriodById(int id){
        Optional<Period> optionalPeriod = Optional.empty();
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_PERIOD_BY_ID)
        ){
            ps.setInt(1, id);
            optionalPeriod = getOptionalPeriod(ps);
        }catch (SQLException e){
            logger.error(e);
        }
        return optionalPeriod;
    }

    private Optional<Period> getOptionalPeriod(PreparedStatement ps) throws SQLException {
        Optional<Period> optionalPeriod = Optional.empty();
        try (ResultSet resultSet = ps.executeQuery()){
            if (resultSet.next()) {
                Period period = new Period();
                period.setId(resultSet.getInt("id"));
                period.setName(resultSet.getString("name"));
                period.setDescription(resultSet.getString("description"));
                optionalPeriod = Optional.of(period);
            }
        }
        return optionalPeriod;
    }
}
