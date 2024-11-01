package com.petrtitov.votingApp.web.restaurant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.petrtitov.votingApp.error.NotFoundException;
import com.petrtitov.votingApp.model.Restaurant;
import com.petrtitov.votingApp.repository.RestaurantRepository;
import com.petrtitov.votingApp.testdata.RestaurantTestData;
import com.petrtitov.votingApp.util.JsonUtil;
import com.petrtitov.votingApp.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.petrtitov.votingApp.testdata.RestaurantTestData.*;
import static com.petrtitov.votingApp.testdata.UserTestData.ADMIN_MAIL;
import static com.petrtitov.votingApp.web.restaurant.AdminRestaurantController.REST_URL;

class AdminRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getById() throws Exception {
        getById(REST_URL_SLASH, RESTAURANT1_ID, RESTAURANT_MATCHER, restaurant1);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        getNotFound(REST_URL_SLASH, NOT_FOUND, repository);
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        delete(REST_URL_SLASH, RESTAURANT1_ID, repository);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        delete(REST_URL_SLASH, RESTAURANT1_ID, repository);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurants));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRest = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRest)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRest.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRest);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant newRest = new Restaurant("");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRest)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(repository.getExisted(RESTAURANT1_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalidId() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setId(NOT_FOUND);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());

        RESTAURANT_MATCHER.assertMatch(repository.getExisted(RESTAURANT1_ID), restaurant1);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalidRestaurantId() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setId(NOT_FOUND);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNotFound());

        Assertions.assertThrows(NotFoundException.class, () -> repository.getExisted(NOT_FOUND));
    }
}