package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.PeriodicalPublication;
import com.swoqe.newSStand.model.entity.Rate;
import com.swoqe.newSStand.model.services.PeriodService;
import com.swoqe.newSStand.model.services.PeriodicalPublicationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "publicationServlet", urlPatterns = {"/publication"})
public class PublicationServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(PublicationServlet.class);

    private final PeriodicalPublicationService publicationService = new PeriodicalPublicationService();
    private final PeriodService periodService = new PeriodService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        try{
            Long id = Long.parseLong(idStr);
            Optional<PeriodicalPublication> optional = publicationService
                    .getPublicationById(id, getServletContext().getRealPath(""));
            if (optional.isPresent()) {
                PeriodicalPublication publication = optional.get();
                List<Rate> rates = periodService.getRatesByPublicationId(publication.getId());
                req.setAttribute("publication", publication);
                req.setAttribute("rates", rates);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/publication.jsp");
                requestDispatcher.forward(req, resp);
            }
            else
                resp.sendRedirect("/catalog");

        }catch (NumberFormatException e){
            logger.error(e);
            resp.sendRedirect("/catalog");
        }
    }
}
