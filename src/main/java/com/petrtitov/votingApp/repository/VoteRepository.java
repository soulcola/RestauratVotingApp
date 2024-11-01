package com.petrtitov.votingApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.petrtitov.votingApp.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v from Vote v WHERE v.userId=:userId AND v.createdAtDate=:date")
    Optional<Vote> findByDateAndUserId(@Param("date") LocalDate date,
                                       @Param("userId") int userId);

    @Query("SELECT v from Vote v WHERE v.userId=:userId")
    List<Vote> getAllByUserId(int userId);
}
