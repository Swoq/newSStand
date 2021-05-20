package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.Genre;
import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.model.services.GenreService;
import com.swoqe.newSStand.model.services.PeriodicalPublicationService;
import com.swoqe.newSStand.util.FilterConfiguration;
import com.swoqe.newSStand.util.OrderBy;
import com.swoqe.newSStand.util.SortingDirection;
import com.swoqe.newSStand.util.Tools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "catalog", value = "/catalog")
public class CatalogServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(CatalogServlet.class);

    private final PeriodicalPublicationService publicationService = new PeriodicalPublicationService();
    private final GenreService genreService = new GenreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String shown = req.getParameter("shown");
        String page = req.getParameter("page");
        String[] genresStr = req.getParameterValues("genres[]");
        String orderByStr = req.getParameter("sortBy");
        String directionStr = req.getParameter("direction");
        String search = req.getParameter("search");

        int currentPage = 1;
        int recordsPerPage = 5;
        Integer[] genresIds = null;
        if (genresStr != null)
            genresIds = Tools.toIntegerArray(genresStr);
        OrderBy orderBy = OrderBy.safeValueOf(orderByStr);
        SortingDirection direction = SortingDirection.safeValueOf(directionStr);
        try{
            if(shown != null)
                recordsPerPage = Integer.parseInt(shown);
            if(page != null)
                currentPage = Integer.parseInt(page);
        }catch (NumberFormatException | DateTimeParseException e){
            logger.error(e);
        }

        FilterConfiguration configuration = new FilterConfiguration.FilterConfigurationBuilder()
                .withRecordsPerPage(recordsPerPage)
                .withPageNumber(currentPage)
                .withGenresIds(genresIds)
                .withOrderBy(orderBy)
                .withSortingDirection(direction)
                .build();

        PeriodicalPublicationService.PublicationsWrapper data;
        if (search != null && !search.equals(""))
            data = publicationService.getPublicationsByName(search, getServletContext().getRealPath(""), configuration);
        else {
            if(genresIds == null)
                data = publicationService
                        .getPublicationsOrderedBy(getServletContext().getRealPath(""), configuration);
            else
                data = publicationService
                        .getPublicationsByGenresOrderedBy(getServletContext().getRealPath(""), configuration);
        }

        int rows = data.getTotalAmount();
        List<PeriodicalPublication> publications = data.getPublications();
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
