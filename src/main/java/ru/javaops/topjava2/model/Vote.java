package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
import java.time.LocalTime;

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
    @JsonIgnore
    private Integer userId;

    @JoinColumn(name = "restaurant_id")
    @Positive
    @NotNull
    private Integer restaurantId;

    @Column(name = "created_at_date")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate createdAtDate;

    @Column(name = "created_at_time")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, type = "String", pattern = "HH:mm:SS")
    private LocalTime createdAtTime;

    public Vote(Integer id, Integer userId, Integer restaurantId, LocalDate createdAtDate, LocalTime createdAtTime) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.createdAtDate = createdAtDate;
        this.createdAtTime = createdAtTime;
    }
}
