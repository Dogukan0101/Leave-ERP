package com.kafein.userERP.repository;

import com.kafein.userERP.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave,Long> {
    void deleteLeaveById(Long leaveId);

    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND " +
            "(l.startDate <= :endDate AND l.endDate >= :startDate)")
    List<Leave> findConflictingLeaves(@Param("userId") Long userId,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.id <> :leaveId AND " +
            "(l.startDate <= :endDate AND l.endDate >= :startDate)")
    List<Leave> findConflictingLeavesExcludingCurrent(@Param("userId") Long userId,
                                                      @Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate,
                                                      @Param("leaveId") Long leaveId);
}
