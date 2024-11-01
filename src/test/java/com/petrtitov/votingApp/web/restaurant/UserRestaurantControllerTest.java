package com.petrtitov.votingApp.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.petrtitov.votingApp.testdata.RestaurantTestData;
import com.petrtitov.votingApp.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.petrtitov.votingApp.TestUtil.userHttpBasic;
import static com.petrtitov.votingApp.testdata.DishTestData.DISH_MATCHER;
import static com.petrtitov.votingApp.testdata.DishTestData.todayDishes;
import static com.petrtitov.votingApp.testdata.RestaurantTestData.RESTAURANT_MATCHER;
import static com.petrtitov.votingApp.testdata.RestaurantTestData.RESTAURANT_MATCHER_WITH_DISHES;
import static com.petrtitov.votingApp.testdata.UserTestData.user;
import static com.petrtitov.votingApp.web.restaurant.UserRestaurantController.REST_URL;

class UserRestaurantControllerTest extends AbstractControllerTest {

    @Test
    void getToday() throws Exception {
        var action = perform(MockMvcRequestBuilders.get(REST_URL + "/today-with-dishes")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        var restaurants = RESTAURANT_MATCHER_WITH_DISHES.readListFromJson(action);

        var dishes = restaurants.stream()
                .flatMap(restaurant -> restaurant.getDishes().stream())
                .toList();
        RESTAURANT_MATCHER.assertMatch(restaurants, RestaurantTestData.restaurantsWithTodayDishes);
        DISH_MATCHER.assertMatch(dishes, todayDishes);
    }
}