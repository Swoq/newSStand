package com.swoqe.newSStand.util;

import com.swoqe.newSStand.controllers.listeners.AppContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {

    final static Logger logger = LogManager.getLogger(AppContextListener.class);

    private ConnectionPool(){
    }

    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance(){
        if (instance==null)
            instance = new ConnectionPool();
        return instance;
    }

    public Connection getConnection(){
        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/newsstandPool");
            c = ds.getConnection();
        } catch (NamingException | SQLException e) {
            logger.error(e);
        }
        return c;
    }
}