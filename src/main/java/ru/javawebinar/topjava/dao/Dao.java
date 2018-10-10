package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Dao
{
    void update(Meal meal);
    void delete(Meal meal);
    void add(Meal meal);
    Meal get(int mealId);
    List<Meal> getAll();
    void addAll(List<Meal> mealList);
}
