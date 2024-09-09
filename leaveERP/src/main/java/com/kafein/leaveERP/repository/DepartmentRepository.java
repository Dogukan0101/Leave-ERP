package com.kafein.leaveERP.repository;

import com.kafein.leaveERP.dtos.DepartmentEmployeeCountDTO;
import com.kafein.leaveERP.dtos.DepartmentOptionsDTO;
import com.kafein.leaveERP.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query("SELECT new com.kafein.leaveERP.dtos.DepartmentOptionsDTO(d.id, d.name) FROM Department d")
    List<DepartmentOptionsDTO> getAllDepartmentOptions();

    Page<Department> findByNameContaining(String departmentName, Pageable pageable);

    @Query("SELECT new com.kafein.leaveERP.dtos.DepartmentEmployeeCountDTO(d.name, COUNT(u.id)) " +
            "FROM Department d LEFT JOIN d.employees u " +
            "GROUP BY d.id, d.name")
    List<DepartmentEmployeeCountDTO> findDepartmentEmployeeCounts();

}
