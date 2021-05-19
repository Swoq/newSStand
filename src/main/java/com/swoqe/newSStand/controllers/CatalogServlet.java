package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.Genre;
import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.model.services.GenreService;
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
    private final GenreService genreService = new GenreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String shown = req.getParameter("shown");
        String page = req.getParameter("page");
        int currentPage = 1;
        int recordsPerPage = 15;
        try{
            if(shown != null)
                recordsPerPage = Integer.parseInt(shown);
            if(page != null)
                currentPage = Integer.parseInt(page);
        }catch (NumberFormatException numberFormatException){
            logger.error(numberFormatException);
        }

        List<PeriodicalPublication> publications = publicationService.getNPublications(recordsPerPage, currentPage,
                getServletContext().getRealPath(""));

        int rows = publicationService.getNumberOfRows();

        int nOfPages = (int) Math.ceil((rows * 1.0) / recordsPerPage);

        List<Genre> genres = genreService.getAllGenres();
        req.setAttribute("noOfPages", nOfPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("recordsPerPage", recordsPerPage);
        req.setAttribute("genres", genres);

        req.setAttribute("publications", publications);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/catalog.jsp");
        requestDispatcher.forward(req, resp);
    }
}
