package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.testdata.RestaurantTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "createdAt");

    public static final int DISH_1_ID = 1;
    public static final int NOT_FOUND = 100;

    public static final LocalDate testDate = LocalDate.of(2023, 1, 1);

    public static final Dish dish1 = new Dish(DISH_1_ID, "Суп томатный", testDate, 15000, restaurant1);

    public static final Dish dish2 = new Dish(DISH_1_ID + 1, "Паста Карбонара", testDate, 30000, restaurant1);

    public static final Dish dish3 = new Dish(DISH_1_ID + 2, "Мясной плов", LocalDate.now(), 25000, restaurant1);

    public static final Dish dish4 = new Dish(DISH_1_ID + 3, "Грибная юшка", LocalDate.now(), 20000, restaurant1);
    public static final Dish dish5 = new Dish(DISH_1_ID + 4, "Куриный бургер", testDate, 20000, restaurant2);
    public static final Dish dish6 = new Dish(DISH_1_ID + 5, "Стейк Рибай", testDate, 80000, restaurant2);
    public static final Dish dish7 = new Dish(DISH_1_ID + 6, "Салат с киноа", LocalDate.now(), 18000, restaurant2);
    public static final Dish dish8 = new Dish(DISH_1_ID + 7, "Омлет с ветчиной", LocalDate.now(), 16000, restaurant2);
    public static final Dish dish9 = new Dish(DISH_1_ID + 8, "Цезарь с креветками", testDate, 35000, restaurant3);
    public static final Dish dish10 = new Dish(DISH_1_ID + 9, "Роллы Филадельфия", testDate, 50000, restaurant3);
    public static final Dish dish11 = new Dish(DISH_1_ID + 10, "Том Ям", LocalDate.now(), 45000, restaurant3);
    public static final Dish dish12 = new Dish(DISH_1_ID + 11, "Удон с говядиной", LocalDate.now(), 38000, restaurant3);

    public static final List<Dish> rest1AllDishes = List.of(dish1, dish2, dish3, dish4);
    public static final List<Dish> rest2AllDishes = List.of(dish5, dish6, dish7, dish8);
    public static final List<Dish> rest3AllDishes = List.of(dish9, dish10, dish11, dish12);

    public static final List<Dish> rest1TodayDishes = List.of(dish3, dish4);
    public static final List<Dish> rest2TodayDishes = List.of(dish7, dish8);
    public static final List<Dish> rest3TodayDishes = List.of(dish11, dish12);

    public static final List<Dish> todayDishes = List.of(dish3, dish4, dish7, dish8, dish11, dish12);

    public static Dish getNew() {
        return new Dish(null, "New", LocalDate.of(2022, 6, 4), 5000);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_1_ID, "UpdatedName", LocalDate.now(), 10000);
    }
}
