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

import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"id", "userId", "createdAt"})
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
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Vote(Integer id, int userId, int restaurantId, LocalDateTime createdAt) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.createdAt = createdAt;
    }
}
