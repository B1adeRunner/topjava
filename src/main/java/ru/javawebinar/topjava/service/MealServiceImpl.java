package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Meal requiredMeal = repository.get(id);
        if( requiredMeal.getUserId() == SecurityUtil.getAuthUserId() ) {
            checkNotFoundWithId(repository.delete(id), id);
        }
        else {
            throw new NotFoundException("Not found entity with id " + id );
        }
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        Meal requiredMeal = repository.get(id);
        if( requiredMeal.getUserId() == SecurityUtil.getAuthUserId() ) {
            return checkNotFoundWithId(repository.get(id), id);
        }
        else {
            throw new NotFoundException("Not found entity with id " + id );
        }
    }

    @Override
    public void update(Meal meal) {
        Meal requiredMeal = repository.get(meal.getId());
        if( requiredMeal.getUserId() == SecurityUtil.getAuthUserId() ) {
            checkNotFoundWithId(repository.save(meal), meal.getId());
        }
        else {
            throw new NotFoundException("Not found entity with id " + meal.getId() );
        }
    }

    @Override
    public List<MealWithExceed> getAll() {
        return MealsUtil.getWithExceeded( new ArrayList<>( repository.getAll( SecurityUtil.getAuthUserId() ) ), MealsUtil.DEFAULT_CALORIES_PER_DAY );
    }

    @Override
    public List<MealWithExceed> getAll(LocalDate activeFromDate, LocalDate activeToDate, LocalTime activeFromTime, LocalTime activeToTime) {
        return MealsUtil.getFilteredWithExceeded( new ArrayList<>( repository.getAll( activeFromDate, activeToDate, SecurityUtil.getAuthUserId() ) ), MealsUtil.DEFAULT_CALORIES_PER_DAY,activeFromTime, activeToTime );
    }
}