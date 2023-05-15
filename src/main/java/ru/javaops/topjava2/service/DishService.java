package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;

    public Dish get(int id) {
        log.info("Get dish item by id: {}", id);
        return dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dish not found with id: " + id));
    }
    public List<Dish> getAll() {
        log.info("Get all dishes");
        return dishRepository.findAll();
    }



    public void delete(int id) {
        log.info("delete menu item with id {}", id);
        dishRepository.delete(id);
    }

    public Dish create(Dish dish, int restaurantId) {
        checkNew(dish);
        Restaurant restaurant = restaurantService.get(restaurantId);
        dish.setRestaurant(restaurant);
        return dishRepository.save(dish);
    }

    public void update(Dish dish, int id) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        dishRepository.save(dish);
    }

    public List<Dish> getByRestAndDate(int restaurantId, LocalDate date) {
        log.info("Get All By Restaurant Id: {} and date: {}", restaurantId, date);
        return dishRepository.getByRestAndDate(restaurantId, date);

    }

}
