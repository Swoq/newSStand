package com.swoqe.newSStand.model.services;

import com.swoqe.newSStand.model.entity.User;
import com.swoqe.newSStand.model.entity.UserRole;
import com.swoqe.newSStand.util.DBCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    final static Logger logger = LogManager.getLogger(UserService.class);

    public void addNewUser(User user){
        String INSERT_USER_SQL = "INSERT INTO users" +
                "  (first_name, last_name, password, role, locked, enable, email) VALUES " +
                " (?,?,?,?,?,?,?);";
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)
        ){
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUserRole().toString());
            preparedStatement.setBoolean(5, user.isLocked());
            preparedStatement.setBoolean(6, user.isEnable());
            preparedStatement.setString(7, user.getEmail());

            preparedStatement.executeUpdate();
            logger.info("DB | User was created: {}", user);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public boolean existByEmail(String email){
        String GET_USER_BY_EMAIL_SQL = "SELECT id, first_name, last_name, password, role, locked, enable, email " +
                "from users where email=?";
        try (
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_SQL)
        ){
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next())
                    return false;
                else {
                    logger.info("DB | User with email {} was found.", email);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password){
        String GET_USER_BY_EMAIL_SQL = "SELECT id, first_name, last_name, password, role, locked, enable, email, account " +
                "from users where email=? and password=?";
        try (
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_SQL)
        ){
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next())
                    return Optional.empty();
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setPassword(resultSet.getString("password"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("role")));
                user.setLocked(resultSet.getBoolean("locked"));
                user.setEnable(resultSet.getBoolean("enable"));
                user.setEmail(resultSet.getString("email"));
                user.setAccount(resultSet.getBigDecimal("account"));

                logger.info("DB | User with email " + email + " was found | " + user);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return Optional.empty();
    }

    public Optional<User> getUserById(Long id){
        String GET_USER_BY_ID_SQL = "SELECT id, first_name, last_name, password, role, locked, enable, email, account " +
                "from users where id=?";
        try (
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID_SQL)
        ){
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next())
                    return Optional.empty();
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setPassword(resultSet.getString("password"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("role")));
                user.setLocked(resultSet.getBoolean("locked"));
                user.setEnable(resultSet.getBoolean("enable"));
                user.setEmail(resultSet.getString("email"));
                user.setAccount(resultSet.getBigDecimal("account"));

                logger.info("DB | User with id: {} was found ", id);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return Optional.empty();
    }

    public void updateUser(User user) {
        String UPDATE_USER_SQL = "update users set " +
                "first_name=?, last_name=?, " +
                "password=?, role=?, " +
                "locked=?, enable=?, " +
                "email=?, account=? " +
                "where id=? ";
        try(
                Connection connection = DBCPDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)
        ){
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUserRole().toString());
            preparedStatement.setBoolean(5, user.isLocked());
            preparedStatement.setBoolean(6, user.isEnable());
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.setBigDecimal(8, user.getAccount());
            preparedStatement.setLong(9, user.getId());

            preparedStatement.executeUpdate();
            logger.info("DB | User was updated: {}", user);
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
