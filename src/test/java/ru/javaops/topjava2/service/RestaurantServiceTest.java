package ru.javaops.topjava2.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.testdata.DishTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava2.testdata.DishTestData.DISH_MATCHER;
import static ru.javaops.topjava2.testdata.RestaurantTestData.*;

@Slf4j
class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void delete() {
        service.delete(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void create() {
        Restaurant created = service.save(getNew());
        int newId = created.id();
        Restaurant newRest = getNew();
        newRest.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRest);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRest);
    }

    @Test
    public void get() {
        Restaurant actual = service.get(RESTAURANT1_ID);
        RESTAURANT_MATCHER.assertMatch(actual, restaurant1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        service.update(updated, RESTAURANT1_ID);
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT1_ID), getUpdated());
    }

    @Test
    public void getAll() {
        RESTAURANT_MATCHER.assertMatch(service.getAll(), restaurants);
    }

    @Test
    public void getWithDishes() {
        Restaurant restaurant = service.getWithDishes(RESTAURANT1_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant1);
        DISH_MATCHER.assertMatch(restaurant.getDishes(), DishTestData.rest1AllDishes);
    }

    @Test
    public void getAllWithDishesToday() {
        List<Restaurant> restaurants = service.getAllTodayWithDishes();
        RESTAURANT_MATCHER.assertMatch(restaurants, restaurantsWithTodayDishes);
        List<Dish> todayDishList = restaurants.stream().flatMap(r -> r.getDishes().stream()).toList();
        DISH_MATCHER.assertMatch(todayDishList, DishTestData.rest1TodayDishes);
    }
}