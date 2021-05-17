package com.swoqe.newSStand.model.services;

import com.swoqe.newSStand.controllers.authentication.RegistrationServlet;
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

        try {
            Connection connection = DBCPDataSource.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUserRole().toString());
            preparedStatement.setBoolean(5, user.isLocked());
            preparedStatement.setBoolean(6, user.isEnable());
            preparedStatement.setString(7, user.getEmail());

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
            logger.info("User was added to database: " + user);
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
    }

    public boolean existByEmail(String email){
        String GET_USER_BY_EMAIL_SQL = "SELECT id, first_name, last_name, password, role, locked, enable, email " +
                "from users where email=?";

        try {
            Connection connection = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_SQL);

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
                return false;
            else {
                logger.info("User with email " + email + " exists.");
                return true;
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return false;
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password){
        String GET_USER_BY_EMAIL_SQL = "SELECT id, first_name, last_name, password, role, locked, enable, email " +
                "from users where email=? and password=?";

        try {
            Connection connection = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_SQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
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

            logger.info("User with email " + email + " was found | " + user);
            return Optional.of(user);
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return Optional.empty();
    }
}
