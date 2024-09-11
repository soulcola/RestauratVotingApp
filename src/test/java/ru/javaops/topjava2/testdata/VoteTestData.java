package ru.javaops.topjava2.testdata;

import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javaops.topjava2.testdata.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.topjava2.testdata.RestaurantTestData.RESTAURANT2_ID;
import static ru.javaops.topjava2.testdata.UserTestData.USER_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "createdAtDate", "createdAtTime");
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static final int VOTE1_ID = 1;
    public static final int TODAY_VOTE_ID = VOTE1_ID + 3;
    public static final int NOT_FOUND = 100;


    public static final LocalTime DEADLINE_AFTER_NOW = LocalTime.now().plusMinutes(1);
    public static final LocalTime DEADLINE_BEFORE_NOW = LocalTime.now().minusMinutes(1);

    public static final Vote vote1 = new Vote(VOTE1_ID, USER_ID, RESTAURANT1_ID, LocalDate.of(2023, 1, 1));
    public static final Vote todayVote1 = new Vote(TODAY_VOTE_ID, USER_ID, RESTAURANT1_ID, LocalDate.now());

    public static final List<Vote> userVotes = List.of(vote1, todayVote1);


    public static Vote getNew() {
        return new Vote(USER_ID, RESTAURANT1_ID, LocalDate.now().plusDays(1));
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, USER_ID, RESTAURANT2_ID, LocalDate.now());
    }
}
