package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if( request.getParameter("userId") == null && SecurityUtil.getAuthUserId() == -1 )
        {
            response.sendRedirect("meals");
            return;
        }
        else if( Boolean.valueOf( request.getParameter("authorised") ) )
        {
            SecurityUtil.setAuthUserId(Integer.valueOf(request.getParameter("userId")));
            doGet(request,response);
            return;
        }
        if( request.getParameter("filter") != null && request.getParameter("filter").equals( "filter" ) )
        {
            doGet(request,response);
            return;
        }

        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.getAuthUserId() );

        if( meal.isNew() )
            mealRestController.create(meal);
        else
            mealRestController.update(meal, meal.getId());
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if( SecurityUtil.getAuthUserId() == -1 )
        {
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        if( request.getParameter("filter") != null && request.getParameter("filter").equals( "filter" ) ) {
            String val = request.getParameter("activeFromTime");
            LocalTime activeFromTime = val != null && !val.isEmpty() ? LocalTime.parse(val) : LocalTime.MIN;
            val = request.getParameter("activeToTime");
            LocalTime activeToTime = val != null && !val.isEmpty() ? LocalTime.parse(val) : LocalTime.MAX;
            val = request.getParameter("activeFromDate");
            LocalDate activeFromDate =  val != null && !val.isEmpty() ? LocalDate.parse(val) : LocalDate.MIN;
            val = request.getParameter("activeToDate");
            LocalDate activeToDate = val != null && !val.isEmpty() ? LocalDate.parse(val) : LocalDate.MAX;

            request.setAttribute("meals", mealRestController.getAll(activeFromDate, activeToDate, activeFromTime, activeToTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        switch (action == null ? "all" : action) {
            case "delete":
                mealRestController.delete(getId(request));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.getAuthUserId() ) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
