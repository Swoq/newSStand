package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.Genre;
import com.swoqe.newSStand.model.entity.Period;
import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.model.services.GenreService;
import com.swoqe.newSStand.model.services.PeriodService;
import com.swoqe.newSStand.model.services.PeriodicalPublicationService;
import com.swoqe.newSStand.util.Tools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "addPublication", value = "/catalog/add")
public class AddPublicationServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(AddPublicationServlet.class);
    private static final String UPLOAD_DIRECTORY = "layouts/static/pp_covers";

    private final PeriodService periodService = new PeriodService();
    private final GenreService genreService = new GenreService();
    private final PeriodicalPublicationService publicationService = new PeriodicalPublicationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Period> periods = periodService.getAllPeriods();
        List<Genre> genres = genreService.getAllGenres();
        req.setAttribute("periods", periods);
        req.setAttribute("genres", genres);

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/layouts/new_publication_page.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String publisher = req.getParameter("publisher");//
        LocalDate publishingDate = LocalDate.parse(req.getParameter("publication_date"));
        String description = req.getParameter("description");
        String[] periods = req.getParameterValues("periods");
        String[] prices = req.getParameterValues("prices");
        String[] genres = req.getParameterValues("genres");
        Optional<Part> optionalPart = Optional.ofNullable(req.getPart("cover_file"));
        File file = null;
        if (optionalPart.isPresent())
            file = Tools.partToFile(optionalPart.get(), getServletContext(), UPLOAD_DIRECTORY);
        List<Genre> genresEntities = genreService.getGenresByNames(genres);
        PeriodicalPublication publication = new PeriodicalPublication.PublicationBuilder()
                .withName(title)
                .withPublisher(publisher)
                .withPublicationDate(publishingDate)
                .withDescription(description)
                .withCoverImg(file)
                .withGenres(genresEntities)
                .withPricesMap(Tools.toHashMap(periods, prices))
                .build();

        publicationService.addPublication(publication);

        resp.sendRedirect("/catalog");
    }


}
