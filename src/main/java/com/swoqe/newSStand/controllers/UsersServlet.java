package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.User;
import com.swoqe.newSStand.model.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "UsersServlet", urlPatterns = {"/users/block", "/users/unblock"})
public class UsersServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(UsersServlet.class);

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        switch (uri){
            case "/users/block":
                this.blockUserPost(req, resp);
                break;
            case "/users/unblock":
                this.unblockUserPost(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void unblockUserPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        if(email != null) {
            Optional<User> optionalUser = userService.getUserByEmail(email);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                user.setLocked(false);
                userService.updateUser(user);
                logger.info("User with email: {} was blocked", email);
                resp.sendRedirect("/");
            }
            else
                logger.info("User with email: {} wasn't found", email);
        }
        else{
            resp.sendRedirect("/");
        }
    }

    private void blockUserPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        if(email != null) {
            Optional<User> optionalUser = userService.getUserByEmail(email);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                user.setLocked(true);
                userService.updateUser(user);
                logger.info("User with email: {} was unblocked", email);
                resp.sendRedirect("/");
            }
            else
                logger.info("User with email: {} wasn't found", email);
        }
        else{
            resp.sendRedirect("/");
        }
    }
}
