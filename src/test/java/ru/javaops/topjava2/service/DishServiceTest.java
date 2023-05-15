package ru.javaops.topjava2.service;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.testdata.RestaurantTestData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava2.testdata.DishTestData.*;
import static ru.javaops.topjava2.testdata.RestaurantTestData.RESTAURANT1_ID;

@Slf4j
class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    void get() {
        Dish actual = service.get(DISH_1);
        DISH_MATCHER.assertMatch(actual, dish1);
    }

    @Test
    void delete() {
        service.delete(DISH_1);
        assertThrows(NotFoundException.class, () -> service.get(DISH_1));
    }

    @Test
    void create() {
        Dish created = service.create(getNew(), RESTAURANT1_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    void createRestNotFound() {
        assertThrows(NotFoundException.class, () -> service.create(getNew(), RestaurantTestData.NOT_FOUND));
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, DISH_1);
        DISH_MATCHER.assertMatch(service.get(DISH_1), getUpdated());
    }

    @Test
    void updateNotFound() {
        Dish updated = getUpdated();
        assertThrows(IllegalRequestDataException.class, () -> service.update(updated, NOT_FOUND));
    }

    @Test
    void getByRestIdAndDate() {
        DISH_MATCHER.assertMatch(
                service.getByRestAndDate(RESTAURANT1_ID, LocalDate.now()),
                rest1TodayDishes);
    }

    @Test
    void createWithException() throws Exception {
        Dish dish1 = new Dish(null, LocalDate.now(), 100);
        Dish dish2 = new Dish("", LocalDate.now(), 100);
        Dish dish3 = new Dish("   ", LocalDate.now(), 100);
        Dish dish4 = new Dish("name", null, 100);
        Dish dish5 = new Dish("name", LocalDate.now(), 0);
        Dish dish6 = new Dish("name", LocalDate.now(), -10);

        validateRootCause(ConstraintViolationException.class, () -> service.create(dish1, RESTAURANT1_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(dish2, RESTAURANT1_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(dish3, RESTAURANT1_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(dish4, RESTAURANT1_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(dish5, RESTAURANT1_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(dish6, RESTAURANT1_ID));
    }
}