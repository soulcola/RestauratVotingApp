package com.petrtitov.votingApp.to;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
public class VoteTo {

    private int restaurantId;

    @Positive
    @ConstructorProperties({"restaurantId"})
    public VoteTo(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
