package com.petrtitov.votingApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.petrtitov.votingApp.model.Dish;
import com.petrtitov.votingApp.repository.DishRepository;
import com.petrtitov.votingApp.repository.RestaurantRepository;

import static com.petrtitov.votingApp.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(save(dish, restaurantId), dish.id());
    }

    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        if (!dish.isNew()) {
            dishRepository.getByIdAndRestaurantId(dish.id(), restaurantId);
        }
        dish.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return dishRepository.save(dish);
    }
}
