package ru.javaops.topjava2.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/vote";

    private final VoteService voteService;

    private static final int USER_ID = 1;

    @GetMapping()
    public Vote get() {
        log.info("Get today vote for user {}", USER_ID);
        return voteService.getByDate(USER_ID, LocalDate.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@RequestBody Vote vote) {
        log.info("Vote user {} for restaurant {}", USER_ID, vote.getRestaurantId());
        Vote created = voteService.create(vote, USER_ID, LocalDateTime.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Vote update(@RequestBody Vote vote) {
        log.info("Update vote user {} for restaurant {}", USER_ID, vote.getRestaurantId());
        return voteService.update(vote, USER_ID, LocalDateTime.now());
    }
}
