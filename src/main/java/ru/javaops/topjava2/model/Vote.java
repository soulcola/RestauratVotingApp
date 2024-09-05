package ru.javaops.topjava2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "vote")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vote extends BaseEntity {

    @JoinColumn(name = "user_id")
    @Positive
    @NotNull
    private Integer userId;

    @JoinColumn(name = "restaurant_id")
    @Positive
    @NotNull
    private Integer restaurantId;

    @NotNull
    @Column(name = "created_at_date")
    private LocalDate createdAtDate;


    public Vote(Integer id, Integer userId, Integer restaurantId, LocalDate createdAtDate) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.createdAtDate = createdAtDate;
    }
}
