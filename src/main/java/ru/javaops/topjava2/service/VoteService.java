package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteService {
    private final LocalTime DEADLINE = LocalTime.of(11, 0);

    private final VoteRepository repository;

    private final RestaurantRepository restaurantRepository;

    public VoteTo getByDateAndUserId(LocalDate date, int userId) {
        return repository.findByDateAndUserId(date, userId)
                .map(vote -> new VoteTo(vote.getRestaurantId()))
                .orElseThrow(() -> new NotFoundException("User " + userId + " didn't vote today"));
    }

    public List<Vote> getAllByUserId(int userId) {
        return repository.getAllByUserId(userId);
    }

    public Vote get(int id) {
        return repository.getExisted(id);
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    public Vote create(Vote vote, int userId) {
        if (!vote.isNew()) {
            repository.getExisted(vote.id());
        }
        vote.setUserId(userId);
        return repository.save(vote);
    }

    public VoteTo saveUserVote(VoteTo vote, int userId, LocalDateTime dateTime) {
        repository.findByDateAndUserId(dateTime.toLocalDate(), userId)
                .ifPresent(v -> checkVoteTime(dateTime, DEADLINE));
        restaurantRepository.getExisted(vote.getRestaurantId());
        repository.save(new Vote(userId, vote.getRestaurantId(), dateTime.toLocalDate(), dateTime.toLocalTime()));
        return vote;
    }

    public static void checkVoteTime(LocalDateTime dateTime, LocalTime deadline) {
        if (dateTime.toLocalTime().isAfter(deadline)) {
            throw new IllegalArgumentException("Vote already exists. You cannot update it after " + deadline);
        }
    }
}
