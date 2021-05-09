package com.swoqe.newSStand.controllers.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    final static Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
//        logger.info("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();
        logger.info("Requested Resource::"+uri);

        HttpSession session = req.getSession(false);

        boolean validSession = (session != null) && (session.getAttribute("user") != null);
        boolean requestedAllowedURI = (uri.endsWith("/") || uri.endsWith("login") || uri.endsWith("registration")
                || uri.endsWith("catalog") || uri.startsWith("/layouts/static"));

        if(!validSession && !requestedAllowedURI){
            logger.error("Unauthorized access request");
            res.sendRedirect("/login");
        }else{
            filterChain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }
}
