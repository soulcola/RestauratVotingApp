package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static ru.javaops.topjava2.util.DateTimeUtil.atStartOfDay;
import static ru.javaops.topjava2.util.DateTimeUtil.atStartOfNextDay;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteService {
    private final LocalTime DEADLINE = LocalTime.of(11, 0);

    private final VoteRepository repository;

    private final RestaurantRepository restaurantRepository;


    public Vote get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found vote with id" + id));
    }


    public Vote getByDate(int userId, LocalDate dateTime) {
        return getOptionalByDate(userId, dateTime)
                .orElseThrow(() -> new NotFoundException("User " + userId + " don't voted today"));
    }

    private Optional<Vote> getOptionalByDate(int userId, LocalDate date) {
        return repository.getByDate(atStartOfDay(date), atStartOfNextDay(date), userId);
    }


    public Vote create(Vote vote, int userId, LocalDateTime dateTime) {
        Assert.notNull(vote, "vote must not be null");
        getOptionalByDate(userId, dateTime.toLocalDate())
                .ifPresent(
                        v -> {
                            throw new IllegalArgumentException("User " + vote.getUserId() + " already voted today");
                        });
        vote.setUserId(userId);
        vote.setCreatedAt(dateTime);
        return repository.save(vote);
    }

    public Vote update(Vote vote, int userId, LocalDateTime dateTime) {
        Vote todayVote = getByDate(vote.getUserId(), dateTime.toLocalDate());
        restaurantRepository.findById(vote.getRestaurantId())
                .orElseThrow(
                        () -> new NotFoundException("Restaurant " + vote.getRestaurantId() + " doesn't exist"));
        DateTimeUtil.checkVoteTime(dateTime, DEADLINE);
        vote.setId(todayVote.getId());
        vote.setUserId(userId);
        vote.setCreatedAt(dateTime);
        return repository.save(vote);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
