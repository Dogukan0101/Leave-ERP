package com.kafein.leaveERP.repository;

import com.kafein.leaveERP.model.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave,Long> {
    void deleteLeaveById(Long leaveId);

    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId AND " +
            "(l.startDate <= :endDate AND l.endDate >= :startDate)")
    List<Leave> findConflictingLeaves(@Param("employeeId") Long employeeId,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId AND l.id <> :leaveId AND " +
            "(l.startDate <= :endDate AND l.endDate >= :startDate)")
    List<Leave> findConflictingLeavesExcludingCurrent(@Param("employeeId") Long employeeId,
                                                      @Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate,
                                                      @Param("leaveId") Long leaveId);

    Page<Leave> findByEmployeeFullNameContaining(String fullName, Pageable pageable);

}
