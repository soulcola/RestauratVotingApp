package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v from Vote v WHERE v.userId=:userId AND v.createdAt>=:startDateTime AND v.createdAt<:endDateTime")
    Optional<Vote> getByDate(@Param("startDateTime") LocalDateTime startDate,
                             @Param("endDateTime") LocalDateTime endDate,
                             @Param("userId") int userId);
}
