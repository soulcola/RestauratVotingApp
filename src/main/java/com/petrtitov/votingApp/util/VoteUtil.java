package com.petrtitov.votingApp.util;

import lombok.experimental.UtilityClass;
import com.petrtitov.votingApp.model.Vote;
import com.petrtitov.votingApp.to.VoteTo;

import java.time.LocalTime;

@UtilityClass
public class VoteUtil {

    public static final LocalTime DEADLINE = LocalTime.of(11, 0);

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getRestaurantId());
    }
}
