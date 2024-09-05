package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "vote")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vote extends BaseEntity {

    //    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Positive
    @NotNull
    private Integer userId;

    //    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @Positive
    @NotNull
    private Integer restaurantId;

    @NotNull
    @Column(name = "created_at_date")
    private LocalDate createdAtDate;

    @NotNull
    @Column(name = "created_at_time")
    private LocalTime createdAtTime;

    public Vote(Integer id, Integer userId, Integer restaurantId, LocalDate createdAtDate, LocalTime createdAtTime) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.createdAtDate = createdAtDate;
        this.createdAtTime = createdAtTime;
    }
}
