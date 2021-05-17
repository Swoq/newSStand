package com.swoqe.newSStand.controllers.authentication;

import com.swoqe.newSStand.model.entity.User;
import com.swoqe.newSStand.model.entity.UserRole;
import com.swoqe.newSStand.model.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "registration", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(RegistrationServlet.class);

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/registration.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(userService.existByEmail(email)){
            RequestDispatcher rd = req.getRequestDispatcher("layouts/registration.jsp");
            req.setAttribute("errorMsg", "User with this email already exists!");
            rd.include(req, resp);
        } else {
            String pwdHash = PasswordEncoder.hashPassword(password);
            User user = new User(firstName, secondName, pwdHash, UserRole.COMMON_USER, email);
            userService.addNewUser(user);

            RequestDispatcher rd = req.getRequestDispatcher("layouts/login.jsp");
            req.setAttribute("regCompleted", true);
            rd.forward(req, resp);

            logger.info("User Registration Completed: {}", user);

        }
    }


}
