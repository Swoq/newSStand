package com.swoqe.newSStand.controllers.listeners;

import com.swoqe.newSStand.util.DBCPDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();

        //initialize DB Connection
        String dbURL = ctx.getInitParameter("dbURL");
        String user = ctx.getInitParameter("dbUser");
        String pwd = ctx.getInitParameter("dbPassword");

        DBCPDataSource dbcpDataSource = new DBCPDataSource(dbURL, user, pwd);
        ctx.setAttribute("DBSource", dbcpDataSource);
        System.out.println("DB Source initialized successfully.");

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        DBCPDataSource con = (DBCPDataSource) servletContextEvent.getServletContext().getAttribute("DBSource");
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
