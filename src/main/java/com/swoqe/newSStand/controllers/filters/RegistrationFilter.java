package com.swoqe.newSStand.controllers.filters;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "RegistrationFilter", urlPatterns = {"/registration"})
public class RegistrationFilter implements Filter {
    final static Logger logger = LogManager.getLogger(RegistrationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if(req.getMethod().equalsIgnoreCase("get"))
            filterChain.doFilter(req, resp);
        else{
            String firstName = req.getParameter("firstName");
            String secondName = req.getParameter("secondName");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String confirmedPassword = req.getParameter("confirmPassword");
            String errorMsg = registrationFieldsValidation(firstName, secondName, email, password, confirmedPassword);

            if (errorMsg != null){
                RequestDispatcher rd = req.getRequestDispatcher("layouts/registration.jsp");
                req.setAttribute("errMsg", errorMsg);
                req.setAttribute("prevEmail", email);
                req.setAttribute("prevFirst", firstName);
                req.setAttribute("prevSecond", secondName);
                rd.include(req, resp);
            }
            else {
                filterChain.doFilter(req, resp);
            }
        }
    }

    @Override
    public void destroy() {
    }

    private String registrationFieldsValidation(String firstName, String lastName, String email, String pwd, String cPwd){
        if(pwd == null || pwd.equals(""))
            return "Password cannot be empty!";

        if(cPwd == null || cPwd.equals(""))
            return "Please confirm password!";

        if (!pwd.equals(cPwd))
            return "Passwords don't match!";

        if(email == null || email.equals(""))
            return "Email can't be null or empty!";

        if(!EmailValidator.getInstance().isValid(email))
            return "Current email isn't valid!";

        if(firstName == null || firstName.equals(""))
            return "First name cannot be empty!";

        if(lastName == null || lastName.equals(""))
            return "Last name cannot be empty!";

        return null;
    }
}
