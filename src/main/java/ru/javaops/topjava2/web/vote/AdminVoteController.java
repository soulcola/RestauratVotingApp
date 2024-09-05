package ru.javaops.topjava2.web.vote;

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
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminVoteController {
    static final String REST_URL_USER = "/api/admin/users/{userId}/votes";
    static final String REST_URL_ROOT = "/api/admin/votes";

    private final VoteService service;

    @GetMapping(value = REST_URL_ROOT)
    public Vote getById(@PathVariable int id,
                        @PathVariable int userId) {
        log.info("Get vote {} for user {}", id, userId);
        return service.get(id);
    }

    @GetMapping(value = REST_URL_USER)
    public List<Vote> getAllByUserId(@PathVariable int userId) {
        log.info("Get all votes for user {}", userId);
        return service.getAllByUserId(userId);
    }

    @PostMapping(value = REST_URL_USER, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@PathVariable int userId,
                                       @RequestBody Vote vote) {
        log.info("Create vote: {} for user: {}", userId, vote);
        checkNew(vote);
        Vote created = service.create(vote, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL_ROOT + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = REST_URL_ROOT + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable int id,
                       @RequestBody Vote vote) {
        log.info("Update vote {}", vote);
        assureIdConsistent(vote, id);
        service.create(vote, id);
    }

    @DeleteMapping(REST_URL_ROOT + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
