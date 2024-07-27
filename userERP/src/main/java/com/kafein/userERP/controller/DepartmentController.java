package com.kafein.userERP.controller;

import com.kafein.userERP.dtos.DepartmentEmployeeCountDTO;
import com.kafein.userERP.dtos.DepartmentOptionsDTO;
import com.kafein.userERP.model.Department;
import com.kafein.userERP.model.Leave;
import com.kafein.userERP.model.User;
import com.kafein.userERP.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartments() {

        List<Department> departments = departmentService.getAllDepartments();

        try {
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

    @GetMapping("/getDepartmentEmployeeCount")
    public List<DepartmentEmployeeCountDTO> getDepartmentEmployeeCounts() {
        return departmentService.getDepartmentEmployeeCounts();
    }

    @GetMapping("/getDepartmentPage")
    public ResponseEntity<Page<Department>> getDepartmentPage(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page,6);
        Page<Department> departmentPage = departmentService.getDepartmentPage(search,pageable);
        try {
            return ResponseEntity.ok(departmentPage);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

    @GetMapping("/getAllDepartmentsForOptions")
    public ResponseEntity<List<DepartmentOptionsDTO>> getAllDepartmentsForOptions() {

        List<DepartmentOptionsDTO> departments = departmentService
                .getAllDepartmentsForOptions();

        try {
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

    @PostMapping("/createDepartment")
    public ResponseEntity<Department> createDepartment(@RequestBody Department departmentRequest) {
        try {
            return ResponseEntity.ok(departmentService.createDepartment(departmentRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/updateDepartmentById")
    public ResponseEntity<Void> updateDepartmentById(@RequestBody Department departmentRequest){
        try {
            departmentService.updateDepartmentById(departmentRequest.getId(),departmentRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    };

    @DeleteMapping("/deleteDepartmentById")
    public ResponseEntity<Void> deleteDepartmentById(@RequestParam Long departmentId){
        try {
            departmentService.deleteDepartmentById(departmentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok().build();
    }

}
