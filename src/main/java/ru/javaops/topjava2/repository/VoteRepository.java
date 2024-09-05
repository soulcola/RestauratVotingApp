package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.error.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v from Vote v WHERE v.userId=:userId AND v.createdAt>=:startDateTime AND v.createdAt<:endDateTime")
    Optional<Vote> findByDateAndUserId(@Param("startDateTime") LocalDateTime startDateTime,
                                      @Param("endDateTime") LocalDateTime endDateTime,
                                      @Param("userId") int userId);

    @Query("SELECT v from Vote v WHERE v.id=:id AND v.userId=:userId")
    Optional<Vote> findByIdAndUserId(int id, int userId);

    @Query("SELECT v from Vote v WHERE v.userId=:userId")
    List<Vote> getAllByUserId(int userId);
}
