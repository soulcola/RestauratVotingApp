package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.testdata.RestaurantTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH_1 = 1;
    public static final int NOT_FOUND = 100;

    public static LocalDate testDate = LocalDate.of(2023, 1, 1);

    public static final Dish dish1 = new Dish(DISH_1, "Суп томатный", testDate, 150, restaurant1);

    public static final Dish dish2 = new Dish(DISH_1 + 1, "Паста Карбонара", testDate, 300, restaurant1);

    public static final Dish dish3 = new Dish(DISH_1 + 2, "Мясной плов", LocalDate.now(), 250, restaurant1);

    public static final Dish dish4 = new Dish(DISH_1 + 3, "Грибная юшка", LocalDate.now(), 200, restaurant1);
    public static final Dish dish5 = new Dish(DISH_1 + 4, "Куриный бургер", LocalDate.now(), 200, restaurant2);
    public static final Dish dish6 = new Dish(DISH_1 + 5, "Стейк Рибай", LocalDate.now(), 800, restaurant2);
    public static final Dish dish7 = new Dish(DISH_1 + 6, "Салат с киноа", LocalDate.now(), 180, restaurant2);
    public static final Dish dish8 = new Dish(DISH_1 + 7, "Омлет с ветчиной", LocalDate.now(), 160, restaurant2);
    public static final Dish dish9 = new Dish(DISH_1 + 8, "Цезарь с креветками", LocalDate.now(), 350, restaurant3);
    public static final Dish dish10 = new Dish(DISH_1 + 9, "Роллы Филадельфия", LocalDate.now(), 500, restaurant3);
    public static final Dish dish11 = new Dish(DISH_1 + 10, "Том Ям", LocalDate.now(), 450, restaurant3);
    public static final Dish dish12 = new Dish(DISH_1 + 11, "Удон с говядиной", LocalDate.now(), 380, restaurant3);

    public static List<Dish> rest1AllDishes = List.of(dish1, dish2, dish3, dish4);

    public static List<Dish> todayDishes = List.of(dish3, dish4, dish7, dish8, dish11, dish12);

    public static Dish getNew() {
        return new Dish(null, "New", LocalDate.of(2022, 6, 4), 500);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_1, "UpdatedName", LocalDate.now(), 10000);
    }
}
