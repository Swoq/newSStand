package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.Period;
import com.swoqe.newSStand.model.services.PeriodService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "addPeriod", value = "/periods/add")
public class AddPeriodServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(AddPeriodServlet.class);

    private final PeriodService periodService = new PeriodService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        periodService.addNewPeriod(new Period(name, description));
        resp.sendRedirect("/catalog");
    }
}
