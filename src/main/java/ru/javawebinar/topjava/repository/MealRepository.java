package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal);

    boolean delete(int id);

    Meal get(int id);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getAll(LocalDate activeFromDate, LocalDate activeToDate, int userId);
}
