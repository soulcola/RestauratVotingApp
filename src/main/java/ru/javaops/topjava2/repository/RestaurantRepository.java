package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Override
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE r.id=:restId")
    Restaurant getWithDishes(@Param("restId") int restaurantId);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE d.createdAt=:date")
    List<Restaurant> getAllByDateWithDishes(@Param("date") LocalDate date);
}
