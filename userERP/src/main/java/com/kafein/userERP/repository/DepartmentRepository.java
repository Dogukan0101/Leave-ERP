package com.kafein.userERP.repository;

import com.kafein.userERP.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query("SELECT d.name FROM Department d")
    List<String> findAllDepartmentNames();

    Department findByName(String name);

}
