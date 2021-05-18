package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.model.services.PeriodicalPublicationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "catalog", value = "/catalog")
public class CatalogServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(CatalogServlet.class);

    private final PeriodicalPublicationService publicationService = new PeriodicalPublicationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sortBy = req.getParameter("sortBy");
        String genre = req.getParameter("genre");
        String shown = req.getParameter("shown");
        int n = 15;
        if(sortBy == null)
            sortBy = "default";
        if(genre == null)
            genre = "default";
        else {
            try{
                n = Integer.parseInt(shown);
            }catch (NumberFormatException numberFormatException){
                logger.error(numberFormatException);
            }
        }

        List<PeriodicalPublication> publications = publicationService.getNPublications(n, getServletContext().getRealPath(""));
        req.setAttribute("publications", publications);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/catalog.jsp");
        requestDispatcher.forward(req, resp);
    }
}
