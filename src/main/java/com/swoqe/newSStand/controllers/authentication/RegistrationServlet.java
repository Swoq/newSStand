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
        String confirmedPassword = req.getParameter("confirmPassword");

        String errorMsg = registrationFieldsValidation(firstName, secondName, email, password, confirmedPassword);

        if (errorMsg != null){
            RequestDispatcher rd = req.getRequestDispatcher("layouts/registration.jsp");
            PrintWriter out = resp.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('"+ errorMsg +"');");
            out.println("</script>");
            logger.info("User Registration error: " + errorMsg);
            rd.include(req, resp);
        }
        else {
            String pwdHash = PasswordEncoder.hashPassword(password);
            User user = new User(firstName, secondName, pwdHash, UserRole.COMMON_USER, email);
            userService.addNewUser(user);

            PrintWriter out = resp.getWriter();

            out.println("<script type=\"text/javascript\">");
            out.println("alert('User Registration Completed');");
            out.println("location='/login';");
            out.println("</script>");

            logger.info("User Registration Completed: " + user);
        }
    }

    private String registrationFieldsValidation(String firstName, String lastName, String email, String pwd, String cPwd){
        if(pwd == null || pwd.equals(""))
            return "Password cannot be empty.";

        if(cPwd == null || cPwd.equals(""))
            return "Please confirm password.";

        if (!pwd.equals(cPwd))
            return "Passwords don't match.";

        if(email == null || email.equals(""))
            return "Email ID can't be null or empty.";

        if(!validate(email))
            return "Current email isn't valid.";

        if(firstName == null || firstName.equals(""))
            return "Name cannot be null or empty.";

        if(lastName == null || lastName.equals(""))
            return "Country cannot be null or empty.";

        if(userService.existByEmail(email))
            return "User with current email already exists";

        return null;
    }

    public static boolean validate(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
