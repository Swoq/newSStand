package com.swoqe.newSStand.controllers;

import com.swoqe.newSStand.model.entity.Subscription;
import com.swoqe.newSStand.model.entity.User;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "accountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(AccountServlet.class);

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
            resp.sendRedirect("/login");
        }

        req.setAttribute("subscriptions", subscriptions);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/account.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String amountStr = req.getParameter("amount");
        User user = (User) req.getSession().getAttribute("user");
        Optional<User> updatedUser = userService.getUserById(user.getId());
        if (updatedUser.isPresent()){
            User u = updatedUser.get();
            HttpSession session = req.getSession();
            try {
                BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));
                u.setAccount(u.getAccount().add(amount));
                this.userService.updateUser(u);
                session.setAttribute("message", "Account has been replenished successfully!");
                resp.sendRedirect("/account");
            } catch (NumberFormatException e){
                logger.error(e);
                session.setAttribute("error", "Invalid amount to replenish!");
                resp.sendRedirect("/account");
            }
        }
        else {
            resp.sendRedirect("/login");
        }
    }
}
