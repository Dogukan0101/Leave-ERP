package com.kafein.leaveERP.repository;

import com.kafein.leaveERP.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    void deleteEmployeeById(Long employeeId);

    Optional<Employee> findEmployeeById(Long employeeId);

    Page<Employee> findByFullNameContaining(String fullName, Pageable pageable);

}
