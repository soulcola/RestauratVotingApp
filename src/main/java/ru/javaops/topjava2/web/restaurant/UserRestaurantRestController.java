package ru.javaops.topjava2.web.restaurant;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.service.RestaurantService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = UserRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantRestController {

    private static final Logger log = LoggerFactory.getLogger(UserRestaurantRestController.class);
    static final String REST_URL = "/api/restaurants";

    private final RestaurantService service;

    @GetMapping
    public List<Restaurant> getToday() {
        log.info("get today's restaurants");
        return service.getAllTodayWithDishes();
    }
}
