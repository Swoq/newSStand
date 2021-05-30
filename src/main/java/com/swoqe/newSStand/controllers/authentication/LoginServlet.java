package com.swoqe.newSStand.controllers.authentication;

import com.swoqe.newSStand.model.entity.User;
import com.swoqe.newSStand.model.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(LoginServlet.class);

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("pwd");
        String rememberMe = req.getParameter("rememberMe");

        String encodedPassword = PasswordEncoder.hashPassword(password);
        Optional<User> optionalUser = userService.getUserByEmailAndPassword(email, encodedPassword);

        if (optionalUser.isPresent() && !optionalUser.get().isLocked()) {
            HttpSession session = req.getSession();
            session.setAttribute("user", optionalUser.get());

            if (rememberMe != null && rememberMe.equals("on"))
                session.setMaxInactiveInterval(24 * 60 * 60); // 24 hour session
            else
                session.setMaxInactiveInterval(30 * 60); // 30 min session

            Cookie userName = new Cookie("email", email);
            userName.setMaxAge(30 * 60);
            resp.addCookie(userName);

            String encodedURL = resp.encodeRedirectURL("/");
            resp.sendRedirect(encodedURL);
        } else {
            RequestDispatcher rd = req.getRequestDispatcher("layouts/login.jsp");
            if (optionalUser.isPresent())
                req.setAttribute("errMsg", "Sorry, this account is blocked. Contact us to find details.");
            else
                req.setAttribute("errMsg", "Email or password is wrong");
            rd.include(req, resp);
        }

    }
}
