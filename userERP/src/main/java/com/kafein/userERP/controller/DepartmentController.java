package com.kafein.userERP.controller;

import com.kafein.userERP.dtos.DepartmentOptionsDTO;
import com.kafein.userERP.model.Department;
import com.kafein.userERP.model.User;
import com.kafein.userERP.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @CrossOrigin
    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartments() {

        List<Department> departments = departmentService.getAllDepartments();

        try {
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

    @CrossOrigin
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

    @CrossOrigin
    @GetMapping("/calculateDepartment")
    public ResponseEntity<Void> calculateDepartment() {
        try {
            departmentService.calculateDepartmentEmployeeNumber();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    };

    @CrossOrigin
    @PostMapping("/createDepartment")
    public ResponseEntity<Department> createDepartment(@RequestBody Department departmentRequest) {
        try {
            return ResponseEntity.ok(departmentService.createDepartment(departmentRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @CrossOrigin
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
