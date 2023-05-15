package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.testdata.RestaurantTestData.restaurant1;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH_1 = 1;
    public static final int TODAY_DISH_1 = 11;
    public static final int NOT_FOUND = 100;

    public static LocalDate testDate = LocalDate.of(2023, 1, 1);

    public static final Dish dish1 = new Dish(DISH_1, "Суп томатный", testDate, 150, restaurant1);

    public static final Dish dish2 = new Dish(DISH_1 + 1, "Паста Карбонара", testDate, 300, restaurant1);

    public static final Dish dish3 = new Dish(TODAY_DISH_1, "Мясной плов", LocalDate.now(), 250, restaurant1);

    public static final Dish dish4 = new Dish(TODAY_DISH_1 + 1, "Грибная юшка", LocalDate.now(), 200, restaurant1);

    public static List<Dish> rest1AllDishes = List.of(dish1, dish2, dish3, dish4);

    public static List<Dish> rest1TodayDishes = List.of(dish3, dish4);

    public static Dish getNew() {
        return new Dish(null, "New", LocalDate.of(2022, 6, 4), 500);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_1, "UpdatedName", LocalDate.now(), 10000);
    }
}
