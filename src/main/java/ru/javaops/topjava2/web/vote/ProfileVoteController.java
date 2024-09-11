package ru.javaops.topjava2.web.vote;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.util.VoteUtil;
import ru.javaops.topjava2.web.AuthUser;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Vote Controller")
public class ProfileVoteController {
    static final String REST_URL = "/api/profile/votes";

    private final VoteService voteService;

    @Cacheable(value = "userCache", key="#authUser.id()")
    @GetMapping
    public VoteTo getToday(@AuthenticationPrincipal @ApiIgnore AuthUser authUser) {
        int userId = authUser.id();
        log.info("Get today vote for user {}", userId);
        return voteService.getByDateAndUserId(LocalDate.now(), userId);
    }

    @PostMapping
    public VoteTo create(@RequestBody @Valid VoteTo vote, @ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("Vote user {} for restaurant {}", userId, vote);
        return voteService.createUpdateUserVote(vote, userId, LocalDateTime.now(), VoteUtil.DEADLINE);
    }
}