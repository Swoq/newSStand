package com.swoqe.newSStand.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public DBCPDataSource(String dbURL, String user, String pwd){
        ds.setUrl(dbURL);
        ds.setUsername(user);
        ds.setPassword(pwd);
    }

    public void close() throws SQLException {
        ds.close();
    }
}
