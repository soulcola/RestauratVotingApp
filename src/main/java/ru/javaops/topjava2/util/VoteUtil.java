package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.to.VoteTo;

import java.time.LocalTime;

@UtilityClass
public class VoteUtil {

    public static final LocalTime DEADLINE = LocalTime.of(11, 0);

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getRestaurantId());
    }
}
