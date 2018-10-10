package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealsDao mealsDao = new MealsDao();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealsDao.getAll(), LocalTime.MIN,LocalTime.MAX,2500);
        request.setAttribute( "meals", mealWithExceeds );
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.valueOf( request.getParameter("id") );
        String method = request.getParameter("method");
        if( method != null && method.equals("edit") )
        {
            Meal meal = mealsDao.get(id);
            request.setAttribute( "meal", meal );
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        }
        else if( method != null && method.equals("editComplete") )
        {
            int calories = Integer.valueOf( request.getParameter("calories") );
            String description = request.getParameter("description");
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
            Meal newMeal = new Meal( dateTime, description, calories, id );
            mealsDao.update(newMeal);
        }
        doGet(request, response);
    }

}
