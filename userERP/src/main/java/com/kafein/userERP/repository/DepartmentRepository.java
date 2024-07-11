package com.kafein.userERP.repository;

import com.kafein.userERP.dtos.DepartmentOptionsDTO;
import com.kafein.userERP.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query("SELECT new com.kafein.userERP.dtos.DepartmentOptionsDTO(d.id, d.name) FROM Department d")
    List<DepartmentOptionsDTO> getAllDepartmentOptions();

    @Query("SELECT d.id, COUNT(u.id) FROM Department d LEFT JOIN d.users u GROUP BY d.id")
    List<Object[]> getDepartmentEmployeeCounts();

}
