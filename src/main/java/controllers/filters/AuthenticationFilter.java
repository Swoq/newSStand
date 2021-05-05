package controllers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();
        System.out.println("Requested Resource::"+uri);

        HttpSession session = req.getSession(false);

        boolean validSession = (session != null) && (session.getAttribute("email") != null);
        boolean requestedAllowedURI = (uri.endsWith("/") || uri.endsWith("login") || uri.endsWith("registration")
                || uri.startsWith("/layouts/assets") || uri.startsWith("/layouts/styles"));

        if (session != null) {
            System.out.println("Session is valid: " + validSession + session + session.getAttribute("email"));
        }

        System.out.println("Request allowed: " + requestedAllowedURI);
        if(!validSession && !requestedAllowedURI){
            System.out.println("Unauthorized access request");
            res.sendRedirect("/login");
        }else{
            filterChain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }
}
