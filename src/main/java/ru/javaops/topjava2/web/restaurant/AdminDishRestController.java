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
import ru.javaops.topjava2.service.DishService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RequiredArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminDishRestController {
    static final String REST_URL_BY_REST = "/api/admin/restaurants/{restaurantId}/dishes";
    static final String REST_URL = "/api/admin/dishes";

    private final DishService service;

    @GetMapping(REST_URL_BY_REST)
    public List<Dish> getAllTodayByRestId(@PathVariable int restaurantId) {
        log.info("get all restaurants");
        return service.getByRestAndDate(restaurantId, LocalDate.now());
    }

    @GetMapping(REST_URL)
    public List<Dish> getAll() {
        log.info("get all dishes");
        return service.getAll();
    }

    @GetMapping(REST_URL + "/{id}")
    public Dish getById(@PathVariable int id) {
        log.info("get dish with id {}", id);
        return service.get(id);
    }

    @PostMapping(value = REST_URL_BY_REST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@PathVariable int restaurantId,
                                    @RequestBody Dish dish) {
        log.info("Add dish to restaurant {}", restaurantId);
        checkNew(dish);
        Dish createdDish = service.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, createdDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdDish);
    }

    @DeleteMapping(REST_URL + "/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete dish with id {}", id);
        service.delete(id);
    }

    @PutMapping(value = REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish,
                       @PathVariable int id) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        service.update(dish, id);
    }
}
