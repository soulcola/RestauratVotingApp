package com.petrtitov.votingApp.testdata;

import com.petrtitov.votingApp.model.Restaurant;
import com.petrtitov.votingApp.web.MatcherFactory;

import java.util.List;

import static com.petrtitov.votingApp.testdata.DishTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_WITH_DISHES = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes.restaurant", "dishes.createdAt");
    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Restaurant1");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Restaurant2");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Restaurant3");

    public static final Restaurant restaurantWithTodayDishes1 = new Restaurant(restaurant1);
    public static final Restaurant restaurantWithTodayDishes2 = new Restaurant(restaurant2);
    public static final Restaurant restaurantWithTodayDishes3 = new Restaurant(restaurant3);

    static {
        restaurant1.setDishes(rest1AllDishes);
        restaurant2.setDishes(rest2AllDishes);
        restaurant3.setDishes(rest3AllDishes);

        restaurantWithTodayDishes1.setDishes(rest1TodayDishes);
        restaurantWithTodayDishes2.setDishes(rest2TodayDishes);
        restaurantWithTodayDishes3.setDishes(rest3TodayDishes);
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
