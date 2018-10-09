package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.model.MealsStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        MealsStorage mealstorage = MealsStorage.getInstance();
        List<MealWithExceed> mealWithExceeds = MealsUtil.convertToMealsWithExeeds(mealstorage.getMealsList(),500);
        request.setAttribute( "meals", mealWithExceeds );
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
