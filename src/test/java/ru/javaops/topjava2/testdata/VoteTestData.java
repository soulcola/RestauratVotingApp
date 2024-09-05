package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDateTime;

import static ru.javaops.topjava2.testdata.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.topjava2.testdata.RestaurantTestData.RESTAURANT2_ID;
import static ru.javaops.topjava2.web.user.UserTestData.USER_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "createdAt");

    public static final int VOTE1_ID = 1;
    public static final int VOTE2_ID = 2;
    public static final int TODAY_VOTE_ID_1 = 4;
    public static final int NOT_FOUND = 100;
    public static final LocalDateTime updatedDt1 = LocalDateTime.of(2023, 1,1, 10,0);
    public static final LocalDateTime updatedDt2 = LocalDateTime.of(2023, 1,1, 13,0);
    public static final LocalDateTime updatedDt3 = LocalDateTime.of(2022, 1,1, 10,0);

    public static Vote vote1 = new Vote(VOTE1_ID, USER_ID, RESTAURANT1_ID, LocalDateTime.of(2023, 1,1, 10,0));
    public static Vote todayVote1 = new Vote(4, USER_ID, RESTAURANT1_ID, LocalDateTime.now());


    public static Vote getNew() {
        return new Vote(USER_ID, RESTAURANT1_ID, LocalDateTime.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, USER_ID, RESTAURANT2_ID, updatedDt1);
    }

    public static Vote getUpdatedAfterDeadline() {
        return new Vote(VOTE1_ID, USER_ID, RESTAURANT2_ID, updatedDt2);
    }

    public static Vote getUpdatedNotToday() {
        return new Vote(VOTE1_ID, USER_ID, RESTAURANT2_ID, updatedDt3);
    }
}
