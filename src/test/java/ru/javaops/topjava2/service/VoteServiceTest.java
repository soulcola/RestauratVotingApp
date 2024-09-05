package ru.javaops.topjava2.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.testdata.RestaurantTestData;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava2.testdata.VoteTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.USER_ID;

@Slf4j
class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    private VoteService service;

    @Test
    void get() {
        Vote actual = service.get(VOTE1_ID);
        VOTE_MATCHER.assertMatch(actual, vote1);
    }

    @Test
    void getByUserIdAndDate() {
        Vote actual = service.getByDateAndUserId(vote1.getCreatedAtDate(), USER_ID);
        VOTE_MATCHER.assertMatch(actual, vote1);
    }

    @Test
    void create() {
        Vote created = service.saveUserVote(RestaurantTestData.RESTAURANT1_ID, USER_ID, LocalDateTime.now());
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId), newVote);
    }

    @Test
    void createUserVoteTwice() {
        service.saveUserVote(RestaurantTestData.RESTAURANT1_ID, USER_ID, LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () -> service.saveUserVote(RestaurantTestData.RESTAURANT1_ID, USER_ID, LocalDateTime.now()));
    }

//    @Test
//    void update() {
//        Vote updated = getUpdated();
//        service.sa(updated, USER_ID, updatedDt1);
//        VOTE_MATCHER.assertMatch(service.get(VOTE1_ID), getUpdated());
//    }

//    @Test
//    void updateAfterDeadline() {
//        Vote updated = getUpdatedAfterDeadline();
//        Assertions.assertThrows(IllegalArgumentException.class, () -> service.sa(updated, USER_ID, updatedDt2));
//    }

//    @Test
//    void updateNotToday() {
//        Vote updated = getUpdatedNotToday();
//        Assertions.assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID, updatedDt3));
//    }

//    @Test
//    void delete() {
//        service.delete(VOTE1_ID);
//        assertThrows(NotFoundException.class, () -> service.get(VOTE1_ID));
//    }
}