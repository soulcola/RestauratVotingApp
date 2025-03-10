package com.petrtitov.votingApp.service;

import com.petrtitov.votingApp.error.NotFoundException;
import com.petrtitov.votingApp.model.Vote;
import com.petrtitov.votingApp.to.VoteTo;
import com.petrtitov.votingApp.util.VoteUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.petrtitov.votingApp.testdata.UserTestData.USER_ID;
import static com.petrtitov.votingApp.testdata.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void getByDateAndUserId() {
        VoteTo actual = service.getByDateAndUserId(vote1.getCreatedAtDate(), USER_ID);
        VOTE_TO_MATCHER.assertMatch(actual, VoteUtil.createTo(vote1));
    }

    @Test
    public void getAllByUserId() {
        List<Vote> actual = service.getAllByUserId(USER_ID);
        VOTE_MATCHER.assertMatch(actual, userVotes);
    }

    @Test
    void create() {
        Vote created = service.createUpdate(getNew());
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId), newVote);
    }

    @Test
    void duplicateDateCreate() {
        Vote todayVote = getNew();
        todayVote.setCreatedAtDate(LocalDate.now());
        assertThrows(DataAccessException.class, () -> service.createUpdate(todayVote));
    }

    @Test
    void userVoteUpdateAfterDeadline() {
        assertThrows(IllegalArgumentException.class,
                () -> service.update(VoteUtil.createTo(vote1), USER_ID, LocalDateTime.now(), DEADLINE_BEFORE_NOW));
    }

    @Test
    void userVoteUpdateBeforeDeadline() {
        Vote updated = getUpdated();
        VoteTo updatedTo = VoteUtil.createTo(updated);
        service.update(updatedTo, USER_ID, LocalDateTime.now(), DEADLINE_AFTER_NOW);
        VOTE_TO_MATCHER.assertMatch(VoteUtil.createTo(service.get(TODAY_VOTE_ID)), VoteUtil.createTo(getUpdated()));
    }

    @Test
    void update() {
        Vote updated = getUpdated();
        service.createUpdate(updated);
        VOTE_MATCHER.assertMatch(service.get(VOTE1_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(VOTE1_ID);
        assertThrows(NotFoundException.class, () -> service.get(VOTE1_ID));
    }
}