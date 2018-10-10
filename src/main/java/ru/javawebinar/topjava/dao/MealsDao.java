package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class MealsDao implements Dao
{
    volatile private int count = 0;
    private Map<Integer,Meal> meals;

    public MealsDao()
    {
        synchronized(this) {
            meals = new ConcurrentHashMap<>();
            meals.put( count+=1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, count) );
            meals.put( count+=1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, count) );
            meals.put( count+=1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, count) );
            meals.put( count+=1, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, count) );
            meals.put( count+=1, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, count) );
            meals.put( count+=1, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, count) );
        }
    }

    @Override
    synchronized public void update(Meal meal) {
        meals.replace( meal.getId(), meal );
    }

    @Override
    synchronized public void delete(Meal meal) {
        meals.remove( meal.getId() );
    }

    @Override
    synchronized public void add(Meal meal) {
        meals.put( count+=1,meal );
    }

    @Override
    synchronized public void addAll(List<Meal> mealsList) {
        count+=1;
        mealsList.forEach( meal -> meals.put( count, meal ) );
    }

    @Override
    synchronized public Meal get(int mealId) {
        return meals.get( mealId );
    }

    @Override
    synchronized public List<Meal> getAll() {
        return new ArrayList<>( meals.values() );
    }
}
