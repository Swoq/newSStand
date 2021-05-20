package com.swoqe.newSStand.controllers.filters;

import com.swoqe.newSStand.model.entity.Genre;
import com.swoqe.newSStand.model.entity.Period;
import com.swoqe.newSStand.model.services.GenreService;
import com.swoqe.newSStand.model.services.PeriodService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebFilter(filterName = "NewPublicationFilter", urlPatterns = {"/catalog/add"})
public class NewPublicationFilter implements Filter {
    final static Logger logger = LogManager.getLogger(NewPublicationFilter.class);

    final private static String[] ACCEPTABLE_FILE_FORMATS = new String[]{".jpg", ".png"};

    private final PeriodService periodService = new PeriodService();
    private final GenreService genreService = new GenreService();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if(req.getMethod().equalsIgnoreCase("get"))
            filterChain.doFilter(req, resp);
        else {
            String title = req.getParameter("title");
            String publisher = req.getParameter("publisher");
            String publicationDate = req.getParameter("publication_date");
            String description = req.getParameter("description");
            Part filePart = req.getPart("cover_file");
            String[] periods = req.getParameterValues("periods");
            String[] prices = req.getParameterValues("prices");
            String[] genres = req.getParameterValues("genres");
            LocalDate parsedDate = null;
            String errorMsg;

            try {
                parsedDate = LocalDate.parse(publicationDate);
            }catch (DateTimeParseException e){
                logger.error(e);
            }

            errorMsg = validateFields(title, publisher, parsedDate, description, filePart, periods, prices, genres);
            if (errorMsg != null){
                ServletContext context = req.getServletContext();
                RequestDispatcher rd = context.getRequestDispatcher("/layouts/new_publication_page.jsp");
                List<Period> allPeriods = periodService.getAllPeriods();
                List<Genre> allGenres = genreService.getAllGenres();
                req.setAttribute("periods", allPeriods);
                req.setAttribute("genres", allGenres);
                req.setAttribute("errMsg", errorMsg);
                req.setAttribute("prevTitle", title);
                req.setAttribute("prevPublisher", publisher);
                req.setAttribute("prevDescription", description);
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

    private String validateFields(String title, String publisher, LocalDate publicationDate, String description,
                                  Part filePart, String[] periods, String[] prices, String[] genres) {
        if(title == null || title.equals(""))
            return "Title cannot be empty!";
        if(title.length() > 255)
            return "Title is too long! (> 255 symbols)";
        if(publisher == null || publisher.equals(""))
            return "Publisher cannot be empty!";
        if(publisher.length() > 255)
            return "Publisher is too long! (> 255 symbols)";
        if(publicationDate == null)
            return "Invalid date format!";
        if(publicationDate.isAfter(LocalDate.now()))
            return "Date should be in the past!";
        if(description == null || description.equals(""))
            return "Description is required!";

        if(periods == null || prices == null || periods.length == 0 || prices.length == 0)
            return "Prices or Periods cannot be empty!";
        if(genres == null || genres.length ==0)
            return "Publication should be related with at least one topic!";

        if(periods.length != prices.length)
            return "Periods amount should be the same as prices!";

        for (String period : periods)
            if (period == null || period.equals(""))
                return "Period cannot be empty!";

        for (String price : prices){
            try {
                BigDecimal n = BigDecimal.valueOf(Double.parseDouble(price));
                if (n.compareTo(BigDecimal.ZERO) < 0)
                    return "Price cannot be negative!";
            } catch (NumberFormatException e){
                return "Invalid price format!";
            }
        }

        if(filePart != null && filePart.getSize() != 0) {
            for (String format : ACCEPTABLE_FILE_FORMATS)
                if (!filePart.getSubmittedFileName().endsWith(format))
                    return "Unacceptable image-file format! (Only jpg & png)";
                else
                    break;
        }

        return null;
    }
}
