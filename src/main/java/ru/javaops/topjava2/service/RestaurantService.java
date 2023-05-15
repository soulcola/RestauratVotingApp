package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.web.SecurityUtilTemp;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.*;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant get(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found with id: " + id));
    }

    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        //TODO checkAdmin
        return restaurantRepository.save(restaurant);
    }

    public Restaurant update(Restaurant restaurant, int id) {
        Assert.notNull(restaurant, "meal must not be null");
        assureIdConsistent(restaurant, id);
        Assert.notNull(restaurant, "restaurant must not be null");
        //TODO checkAdmin
        return checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }

    public void delete(int restaurantId) {
        //TODO checkAdmin
        checkNotFoundWithId((restaurantRepository.delete(restaurantId) != 0), restaurantId);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant getWithDishes(int restaurantId) {
        return restaurantRepository.getWithDishes(restaurantId);
    }

    public List<Restaurant> getAllTodayWithDishes() {
        return restaurantRepository.getAllByDateWithDishes(LocalDate.now());
    }

}
