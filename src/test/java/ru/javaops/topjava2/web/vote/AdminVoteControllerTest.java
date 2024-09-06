package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.testdata.DishTestData;
import ru.javaops.topjava2.testdata.RestaurantTestData;
import ru.javaops.topjava2.testdata.VoteTestData;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;
import ru.javaops.topjava2.testdata.UserTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.TestUtil.userHttpBasic;
import static ru.javaops.topjava2.testdata.DishTestData.*;
import static ru.javaops.topjava2.testdata.DishTestData.DISH_1_ID;
import static ru.javaops.topjava2.testdata.RestaurantTestData.*;
import static ru.javaops.topjava2.testdata.VoteTestData.*;
import static ru.javaops.topjava2.testdata.VoteTestData.NOT_FOUND;
import static ru.javaops.topjava2.testdata.UserTestData.*;
import static ru.javaops.topjava2.web.vote.ProfileVoteController.REST_URL;

class AdminVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = AdminVoteController.REST_URL_ROOT + '/';
    private static final String REST_URL_USER_SLASH = AdminVoteController.REST_URL_USER ;


    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getById() throws Exception {
        getById(REST_URL_SLASH, VOTE1_ID, VOTE_MATCHER, vote1);
    }

    @Test
    void getUnauth() throws Exception {
        getUnauth(REST_URL_SLASH, VOTE1_ID);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        getNotFound(REST_URL_SLASH, VoteTestData.NOT_FOUND, repository);
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        delete(REST_URL_SLASH, VOTE1_ID, repository);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        deleteNotFound(REST_URL_SLASH, VoteTestData.NOT_FOUND);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_USER_SLASH, UserTestData.USER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(userVotes));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(repository.getExisted(VOTE1_ID), updated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Vote invalid = new Vote(VOTE1_ID, 0, 0, null);

        perform(MockMvcRequestBuilders.put(REST_URL, NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalidId() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        updated.setId(NOT_FOUND);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalidRestaurantId() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setId(NOT_FOUND);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + NOT_FOUND)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());

        Assertions.assertThrows(NotFoundException.class, () -> repository.getExisted(NOT_FOUND));
    }
}