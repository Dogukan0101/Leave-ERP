package com.kafein.userERP.repository;

import com.kafein.userERP.dtos.LeaveDTO;
import com.kafein.userERP.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave,Long> {
    void deleteLeaveById(Long leaveId);

    Optional<LeaveDTO> findLeaveById(Long leaveId);
}
