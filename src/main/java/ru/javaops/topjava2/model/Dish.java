package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import ru.javaops.topjava2.HasId;

import java.time.LocalDate;

@Entity
@Table(name = "dish")
@Getter
@Setter
public class Dish extends NamedEntity implements HasId {

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate createdAt;


    @Column(name = "price", nullable = false)
    @Positive
    private long price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, LocalDate createdAt, long price, Restaurant restaurant) {
        super(id, name);
        this.createdAt = createdAt;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String name, LocalDate createdAt, long price) {
        this(id, name, createdAt, price, null);
    }

    public Dish(String name, LocalDate createdAt, long price) {
        this(null, name, createdAt, price);
    }

    @JsonProperty("price")
    public double getPrice() {
        return price / 100.0;
    }

    public void setPrice(double price) {
        this.price = (long) (price * 100);
    }
}
