package ru.javaops.topjava2.web.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.service.VoteService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class ProfileVoteController {
    static final String REST_URL = "/api/profile/votes";

    private final VoteService voteService;

    private static final int USER_ID = 1;

    @GetMapping
    public int get() {
        log.info("Get today vote for user {}", USER_ID);
        return voteService.getByDateAndUserId(LocalDate.now(), USER_ID).getRestaurantId();
    }
    @PostMapping
    public int create(@RequestParam int restaurantId) {
        log.info("Vote user {} for restaurant {}", USER_ID, restaurantId);
        return voteService.saveUserVote(restaurantId, USER_ID, LocalDateTime.now()).getRestaurantId();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public int update(@RequestParam int restaurantId) {
        log.info("Update vote user {} for restaurant {}", USER_ID, restaurantId);
        return voteService.saveUserVote(restaurantId, USER_ID, LocalDateTime.now()).getRestaurantId();
    }
}
