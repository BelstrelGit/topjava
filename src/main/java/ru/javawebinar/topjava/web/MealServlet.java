package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by belstrel on 04.11.17.
 */
public class MealServlet extends HttpServlet {

   private static final Logger log = getLogger(MealServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to users");
        resp.sendRedirect("meals.jsp");
        //dep  request.getRequestDispatcher("/users.jsp").forward(request, response);

    }
}
