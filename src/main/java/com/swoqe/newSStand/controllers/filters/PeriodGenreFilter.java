package com.swoqe.newSStand.controllers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "periodGenreFilter", urlPatterns = {"/periods/add", "/genres/add"})
public class PeriodGenreFilter implements Filter {
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
            String name = req.getParameter("name");
            String errorMsg = validate(name);

            if (errorMsg != null){
                ServletContext context = req.getServletContext();
                RequestDispatcher rd = context.getRequestDispatcher("layouts/static/templates/header.jsp");
                req.setAttribute("adminInfo", errorMsg);
                rd.include(req, resp);
            }
            else {
                filterChain.doFilter(req, resp);
            }
        }
    }

    private String validate(String name) {
        if(name == null || name.equals(""))
            return "Name cannot be empty!";
        if(name.length() > 255)
            return "Name is too long!";
        return null;
    }

    @Override
    public void destroy() {
    }
}
