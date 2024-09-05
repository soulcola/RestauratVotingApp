package ru.javaops.topjava2.web.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class ProfileVoteController {
    static final String REST_URL = "/api/profile/votes";

    private final VoteService voteService;

    @GetMapping
    public Vote getToday(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("Get today vote for user {}", userId);
        return voteService.getByDateAndUserId(LocalDate.now(), userId);
    }

    @PostMapping
    public int create(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("Vote user {} for restaurant {}", userId, restaurantId);
        return voteService.saveUserVote(restaurantId, userId, LocalDateTime.now()).getRestaurantId();
    }

    @PutMapping
    public int update(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("Update vote user {} for restaurant {}", userId, restaurantId);
        return voteService.saveUserVote(restaurantId, userId, LocalDateTime.now()).getRestaurantId();
    }
}