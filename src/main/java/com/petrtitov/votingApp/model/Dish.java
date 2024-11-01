package com.petrtitov.votingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.petrtitov.votingApp.HasId;
import com.petrtitov.votingApp.config.MoneyDeserializer;
import com.petrtitov.votingApp.config.MoneySerializer;

import java.time.LocalDate;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
public class Dish extends NamedEntity implements HasId {

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate createdAt;


    @Column(name = "price", nullable = false)
    @Positive
    @JsonDeserialize(using = MoneyDeserializer.class)
    @JsonSerialize(using = MoneySerializer.class)
    private long price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

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

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", restaurant=" + restaurant +
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }
}
