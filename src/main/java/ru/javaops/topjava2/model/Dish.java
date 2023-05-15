package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.javaops.topjava2.HasId;

import java.time.LocalDate;

@Entity
@Table(name = "dish")
@ToString(callSuper = true, exclude = "restaurant")
@Getter
@Setter
public class Dish extends NamedEntity implements HasId {

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate createdAt;

    @Column(name = "price", nullable = false)
    @Positive
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, LocalDate createdAt, double price, Restaurant restaurant) {
        super(id, name);
        this.createdAt = createdAt;
        this.price = price;
        this.restaurant = restaurant;
    }
    public Dish(Integer id, String name, LocalDate createdAt, double price) {
        this(id, name, createdAt, price, null);
    }

    public Dish(String name, LocalDate createdAt, double price) {
        this(null, name, createdAt, price);
    }
}
