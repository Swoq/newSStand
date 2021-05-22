package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.Rate;
import com.swoqe.newSStand.model.entity.Subscription;
import com.swoqe.newSStand.model.entity.User;
import com.swoqe.newSStand.model.services.PeriodService;
import com.swoqe.newSStand.model.services.SubscriptionService;
import com.swoqe.newSStand.model.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "subscriptionServlet", urlPatterns = {"/subscriptions/add", "/subscriptions/delete"})
public class SubscriptionServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(SubscriptionServlet.class);

    private final UserService userService = new UserService();
    private final SubscriptionService subscriptionService = new SubscriptionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        switch (uri){
            case "/subscriptions/add":
                this.doSubscriptionsAdd(req, resp);
                break;
            case "/subscriptions/delete":
                this.doSubscriptionsDelete(req, resp);
                break;
            default:
                resp.sendRedirect("/");
                break;
        }
    }

    private void doSubscriptionsAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        Optional<User> optionalUser = userService.getUserById(sessionUser.getId());
        if (optionalUser.isPresent()){
            try{
                HttpSession session = req.getSession(false);
                User user = optionalUser.get();
                Long rateId = Long.parseLong(req.getParameter("rateId"));
                Optional<Subscription> optionalSubscription = subscriptionService.getSubscriptionByUserIdAndRateId(user, rateId);
                if(optionalSubscription.isPresent()){
                    session.setAttribute("error", "You are already subscribed to this publication!");
                }
                else{
                    subscriptionService.doUserSubscribe(user.getId(), rateId);
                    session.setAttribute("message", "Subscription has been added successfully!");
                }
                resp.sendRedirect("/account");
            }
            catch (NumberFormatException e){
                logger.error(e);
                resp.sendRedirect("/catalog");
            }
        }
        else {
            resp.sendRedirect("/login");
        }
    }

    private void doSubscriptionsDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        Optional<User> user = userService.getUserById(sessionUser.getId());
        if (user.isPresent()){

        }
        else {
            resp.sendRedirect("/login");
        }
    }
}
