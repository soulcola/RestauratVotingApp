package ru.javaops.topjava2.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;

import java.net.URI;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RequiredArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminDishController {
    static final String REST_URL = "/api/admin/dishes";

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping(REST_URL + "/by-restaurant")
    public List<Dish> getAllByRestaurantId(@RequestParam int restaurantId) {
        log.info("get all dishes for restaurant {}", restaurantId);
        return repository.getAllByRestaurantId(restaurantId);
    }

    @GetMapping(REST_URL + "/{id}")
    public Dish getById(@PathVariable int id) {
        log.info("get dish {}", id);
        return repository.getExisted(id);
    }

    @PostMapping(value = REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@RequestBody Dish dish,
                                       @RequestParam int restaurantId) {
        log.info("Add dish {} to restaurant {}", dish, restaurantId);
        checkNew(dish);
        Restaurant restaurantRef = restaurantRepository.getReferenceById(restaurantId);
        dish.setRestaurant(restaurantRef);
        Dish created = repository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish {}", id);
        repository.deleteExisted(id);
    }

    @PutMapping(value = REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish,
                       @RequestParam int restaurantId,
                       @PathVariable int id) {
        log.info("update {} with id {} for restaurant {}", dish, id, restaurantId);
        assureIdConsistent(dish, id);
        repository.getByIdAndRestaurantId(id, restaurantId);
        repository.save(dish);
    }
}
