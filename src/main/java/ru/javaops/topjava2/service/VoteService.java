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

    public Vote createUpdate(Vote vote) {
        if (!vote.isNew()) {
            repository.getExisted(vote.id());
        }
        return repository.save(vote);
    }

    public VoteTo createUpdateUserVote(VoteTo vote, int userId, LocalDateTime dateTime, LocalTime deadline) {
        Vote newVote = new Vote(userId, vote.getRestaurantId(), dateTime.toLocalDate());
        repository.findByDateAndUserId(dateTime.toLocalDate(), userId)
                .ifPresent(vote1 -> {
                    checkVoteTime(dateTime, deadline);
                    newVote.setId(vote1.getId());
                    newVote.setUserId(userId);
                });
        restaurantRepository.getExisted(vote.getRestaurantId());
        createUpdate(newVote);
        return vote;
    }

    public static void checkVoteTime(LocalDateTime dateTime, LocalTime deadline) {
        if (dateTime.toLocalTime().isAfter(deadline)) {
            throw new IllegalArgumentException("Vote already exists. You cannot update it after " + deadline);
        }
    }
}
