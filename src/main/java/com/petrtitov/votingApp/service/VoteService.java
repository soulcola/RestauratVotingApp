package com.petrtitov.votingApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.petrtitov.votingApp.error.NotFoundException;
import com.petrtitov.votingApp.model.Vote;
import com.petrtitov.votingApp.repository.RestaurantRepository;
import com.petrtitov.votingApp.repository.VoteRepository;
import com.petrtitov.votingApp.to.VoteTo;

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

    @Transactional
    public Vote createUpdate(Vote vote) {
        if (!vote.isNew()) {
            repository.getExisted(vote.id());
        }
        return repository.save(vote);
    }

    @Transactional
    public VoteTo create(VoteTo vote, int userId, LocalDateTime dateTime) {
        Vote newVote = new Vote(userId, vote.getRestaurantId(), dateTime.toLocalDate());
        createUpdate(newVote);
        return vote;
    }

    @Transactional
    public VoteTo update(VoteTo vote, int userId, LocalDateTime dateTime, LocalTime deadline) {
        Vote newVote = new Vote(userId, vote.getRestaurantId(), dateTime.toLocalDate());
        Vote existingVote = repository.findByDateAndUserId(dateTime.toLocalDate(), userId)
                .orElseThrow(() -> new NotFoundException("Vote not found for userId=" + userId + " on date=" + dateTime.toLocalDate()));
        checkVoteTime(dateTime, deadline);
        newVote.setId(existingVote.getId());

//        Проверка id ресторана, т.к. у нас в Vote не Restaurant, а id, и может засеттиться несуществующий
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
