package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;

import java.util.List;

import static ru.javaops.topjava2.testdata.DishTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Restaurant1");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Restaurant2");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Restaurant3");

    public static final Restaurant restaurantWithDishes1 = new Restaurant(restaurant1);
    public static final Restaurant restaurantWithDishes2 = new Restaurant(restaurant2);
    public static final Restaurant restaurantWithDishes3 = new Restaurant(restaurant3);

    public static final Restaurant restaurantWithTodayDishes1 = new Restaurant(restaurant1);
    public static final Restaurant restaurantWithTodayDishes2 = new Restaurant(restaurant2);
    public static final Restaurant restaurantWithTodayDishes3 = new Restaurant(restaurant3);

    static {
        restaurantWithDishes1.setDishes(List.of(dish1, dish2, dish3, dish4));
        restaurantWithDishes2.setDishes(List.of(dish5, dish6, dish7, dish8));
        restaurantWithDishes3.setDishes(List.of(dish9, dish10, dish11, dish12));

        restaurantWithTodayDishes1.setDishes(List.of(dish3, dish4));
        restaurantWithTodayDishes2.setDishes(List.of(dish7, dish8));
        restaurantWithTodayDishes3.setDishes(List.of(dish11, dish12));
    }

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);

    public static final List<Restaurant> restaurantsWithTodayDishes = List.of(restaurantWithTodayDishes1, restaurantWithTodayDishes2, restaurantWithTodayDishes3);

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "UpdatedName");
    }
}
