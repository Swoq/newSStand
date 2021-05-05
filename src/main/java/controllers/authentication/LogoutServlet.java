package controllers.authentication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")){
                    System.out.println("JSESSIONID="+cookie.getValue());
                }
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }

        //invalidate the session if exists
        HttpSession session = req.getSession(false);
        if(session != null){
            System.out.println("Logout User="+session.getAttribute("email"));
            session.removeAttribute("email");
            session.invalidate();
        }
        resp.sendRedirect("/");
    }
}
