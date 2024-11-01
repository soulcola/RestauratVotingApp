package com.petrtitov.votingApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.petrtitov.votingApp.model.Restaurant;
import com.petrtitov.votingApp.repository.RestaurantRepository;

import static com.petrtitov.votingApp.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(save(restaurant), restaurant.id());
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "meal must not be null");
        if (!restaurant.isNew()) {
            restaurantRepository.getExisted(restaurant.id());
        }
        return restaurantRepository.save(restaurant);
    }
}
