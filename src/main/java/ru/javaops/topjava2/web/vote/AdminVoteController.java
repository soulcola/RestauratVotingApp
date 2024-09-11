package ru.javaops.topjava2.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;

import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin Vote Controller")
public class AdminVoteController {
    static final String REST_URL_USER = "/api/admin/users/{userId}/votes";
    static final String REST_URL_ROOT = "/api/admin/votes";

    private final VoteService service;

    @Operation(summary = "Get vote by id")
    @GetMapping(value = REST_URL_ROOT + "/{id}")
    public Vote getById(@PathVariable int id) {
        log.info("Get vote {}", id);
        return service.get(id);
    }

    @Operation(summary = "Get all votes by user id")
    @GetMapping(value = REST_URL_USER)
    public List<Vote> getAllByUserId(@PathVariable int userId) {
        log.info("Get all votes for user {}", userId);
        return service.getAllByUserId(userId);
    }

    @Operation(summary = "update vote by id")
    @CacheEvict(value = "userCache", key = "#vote.userId")
    @PutMapping(value = REST_URL_ROOT + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id,
                       @RequestBody @Valid Vote vote) {
        log.info("Update vote {}", vote);
        assureIdConsistent(vote, id);
        service.createUpdate(vote);
    }

    @Operation(summary = "Delete vote by id")
    @CacheEvict(value = "userCache", allEntries = true)
    @DeleteMapping(REST_URL_ROOT + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
