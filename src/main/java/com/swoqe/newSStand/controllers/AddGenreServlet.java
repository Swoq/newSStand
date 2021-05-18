package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.Genre;
import com.swoqe.newSStand.model.entity.Period;
import com.swoqe.newSStand.model.services.GenreService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "addGenreServlet", urlPatterns = {"/genres/add"})
public class AddGenreServlet extends HttpServlet {

    private final GenreService genreService = new GenreService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        genreService.addNewGenre(new Genre(name, description));
        resp.sendRedirect("/catalog");
    }
}
