package com.swoqe.newSStand.controllers.filters;

import com.swoqe.newSStand.controllers.authentication.LoginServlet;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/login"})
public class LoginFilter implements Filter {
    final static Logger logger = LogManager.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if(req.getMethod().equalsIgnoreCase("get"))
            filterChain.doFilter(req, resp);
        else{
            String email = req.getParameter("email");
            String pwd = req.getParameter("pwd");
            String errorMsg = loginFieldsValidation(email, pwd);

            if (errorMsg != null){
                RequestDispatcher rd = req.getRequestDispatcher("layouts/login.jsp");
                req.setAttribute("errMsg", errorMsg);
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

    private String loginFieldsValidation(String email, String password){
        if(email == null || email.equals(""))
            return "Email cannot be empty!";

        if(!EmailValidator.getInstance().isValid(email))
            return "Invalid email format!";

        if(password == null || password.equals(""))
            return "Password cannot be empty!";

        return null;
    }
}
