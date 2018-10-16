package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController extends AbstractMealController{

    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    public List<MealWithExceed> getAll(LocalDate activeFromDate, LocalDate activeToDate, LocalTime activeFromTime, LocalTime activeToTime) {
        return super.getAll(activeFromDate, activeToDate, activeFromTime, activeToTime);
    }

    public Meal get(int id) {
        return super.get(id);
    }

    public Meal create(Meal meal) {
        return super.create(meal);
    }

    public void delete(int id) {
        super.delete(id);
    }

    public void update(Meal meal, int id) {
        super.update(meal,id);
    }
}