package com.swoqe.newSStand.controllers.listeners;

import com.swoqe.newSStand.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    final static Logger logger = LogManager.getLogger(AppContextListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.getConnection();
        logger.info("Connection pool initialization has been completed.");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) { }

}
