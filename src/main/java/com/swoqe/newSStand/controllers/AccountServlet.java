package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.Subscription;
import com.swoqe.newSStand.model.entity.User;
import com.swoqe.newSStand.model.services.SubscriptionService;
import com.swoqe.newSStand.model.services.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "accountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final SubscriptionService subscriptionService = new SubscriptionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Optional<User> updatedUser = userService.getUserById(user.getId());
        List<Subscription> subscriptions = new ArrayList<>();
        if(updatedUser.isPresent()){
            User tUser = updatedUser.get();
            req.setAttribute("user", tUser);
            subscriptions = subscriptionService.getSubscriptionsByUser(tUser);
        }
        else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/login.jsp");
            requestDispatcher.forward(req, resp);
        }

        req.setAttribute("subscriptions", subscriptions);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/account.jsp");
        requestDispatcher.forward(req, resp);
    }
}
