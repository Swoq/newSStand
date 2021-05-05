package controllers.authentication;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("layouts/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pwd = req.getParameter("pwd");
        String rememberMe = req.getParameter("rememberMe");

        boolean validated = true;

        if(validated){
            HttpSession session = req.getSession();
            session.setAttribute("email", email);

            if (rememberMe != null && rememberMe.equals("on"))
                session.setMaxInactiveInterval(24*60*60); // 24 hour session
            else
                session.setMaxInactiveInterval(30*60); // 30 min session

            Cookie userName = new Cookie("email", email);
            userName.setMaxAge(30*60);
            resp.addCookie(userName);

            String encodedURL = resp.encodeRedirectURL("/");
            resp.sendRedirect(encodedURL);
        }else{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("layouts/login.jsp");
            PrintWriter out= resp.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(req, resp);
        }
    }
}
