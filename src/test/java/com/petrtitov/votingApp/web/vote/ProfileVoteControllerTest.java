package com.petrtitov.votingApp.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.petrtitov.votingApp.service.VoteService;
import com.petrtitov.votingApp.testdata.UserTestData;
import com.petrtitov.votingApp.testdata.VoteTestData;
import com.petrtitov.votingApp.to.VoteTo;
import com.petrtitov.votingApp.util.JsonUtil;
import com.petrtitov.votingApp.util.VoteUtil;
import com.petrtitov.votingApp.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.petrtitov.votingApp.testdata.UserTestData.USER_MAIL;
import static com.petrtitov.votingApp.testdata.VoteTestData.VOTE_TO_MATCHER;
import static com.petrtitov.votingApp.testdata.VoteTestData.todayVote1;
import static com.petrtitov.votingApp.web.vote.ProfileVoteController.REST_URL;

public class ProfileVoteControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteService service;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.createTo(todayVote1)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        VoteTo updated = VoteUtil.createTo(VoteTestData.getUpdated());
        var result = perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print());
        if (LocalTime.now().isBefore(VoteUtil.DEADLINE)) {
            result.andExpect(status().isOk());
            VOTE_TO_MATCHER.assertMatch(updated, service.getByDateAndUserId(LocalDate.now(), UserTestData.USER_ID));
        } else {
            result.andExpect(status().isUnprocessableEntity());
        }
    }
}
