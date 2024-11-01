package com.petrtitov.votingApp.web.restaurant;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.petrtitov.votingApp.model.Dish;
import com.petrtitov.votingApp.repository.DishRepository;
import com.petrtitov.votingApp.testdata.DishTestData;
import com.petrtitov.votingApp.util.JsonUtil;
import com.petrtitov.votingApp.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.petrtitov.votingApp.TestUtil.userHttpBasic;
import static com.petrtitov.votingApp.testdata.DishTestData.*;
import static com.petrtitov.votingApp.testdata.RestaurantTestData.RESTAURANT1_ID;
import static com.petrtitov.votingApp.testdata.RestaurantTestData.RESTAURANT2_ID;
import static com.petrtitov.votingApp.testdata.UserTestData.ADMIN_MAIL;
import static com.petrtitov.votingApp.testdata.UserTestData.admin;
import static com.petrtitov.votingApp.web.restaurant.AdminDishController.REST_URL;


public class AdminDishControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private DishRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getById() throws Exception {
        getById(REST_URL_SLASH, DISH_1_ID, DISH_MATCHER, dish1);
    }

    @Test
    void getUnauth() throws Exception {
        getUnauth(REST_URL_SLASH, DISH_1_ID);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        getNotFound(REST_URL_SLASH, NOT_FOUND, repository);
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        delete(REST_URL_SLASH, DISH_1_ID, repository);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        deleteNotFound(REST_URL_SLASH, NOT_FOUND);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        System.out.println(action);
        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createForInvalidRestaurant() throws Exception {
        Dish newDish = DishTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(NOT_FOUND))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish newDish = new Dish("", null, 0);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + DISH_1_ID)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID))
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        DISH_MATCHER.assertMatch(repository.getExisted(DISH_1_ID), updated);
    }

    @Test
    void updateNotOwn() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + DISH_1_ID)
                .param("restaurantId", String.valueOf(RESTAURANT2_ID))
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
