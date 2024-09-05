package ru.javaops.topjava2.to;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Data
public class VoteTo implements Serializable {

    private int RestaurantId;

    @Positive
    @ConstructorProperties({"restaurantId"})
    public VoteTo(int restaurantId) {
        RestaurantId = restaurantId;
    }
}
