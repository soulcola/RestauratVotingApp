package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;

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

    private Vote getExistedByIdAndUserId(int id, int userId) {
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("Vote with id " + id + "and user id " + userId + "not found"));
    }

    public Vote getByDateAndUserId(LocalDate date, int userId) {
        return repository.findByDateAndUserId(date, userId)
                .orElseThrow(() -> new NotFoundException("User " + userId + " don't voted today"));
    }

    public Vote get(int id) {
        return repository.getExisted(id);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Vote> getAllByUserId(int userId) {
        return repository.getAllByUserId(userId);
    }

    public Vote create(Vote vote, int userId) {
        if (!vote.isNew()) {
            getExistedByIdAndUserId(vote.id(), userId);
        }
        vote.setUserId(userId);
        return repository.save(vote);
    }

    public Vote saveUserVote(Vote vote, int userId, LocalDateTime dateTime) {
        repository.findByDateAndUserId(dateTime.toLocalDate(), userId)
                .ifPresent(v -> checkVoteTime(dateTime, DEADLINE));
        restaurantRepository.getExisted(vote.getRestaurantId());
        vote.setUserId(userId);
        vote.setCreatedAtDate(dateTime.toLocalDate());
        vote.setCreatedAtTime(dateTime.toLocalTime());
        return repository.save(vote);
    }

    public static void checkVoteTime(LocalDateTime dateTime, LocalTime deadline) {
        if (dateTime.toLocalTime().isAfter(deadline)) {
            throw new IllegalArgumentException("Vote already exists. You cannot update it after " + deadline);
        }
    }
}
