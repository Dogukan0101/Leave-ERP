package com.kafein.leaveERP.controller;

import com.kafein.leaveERP.model.Employee;
import com.kafein.leaveERP.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {

        List<Employee> employees = employeeService.getAllEmployee();

        try {
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

    @GetMapping("/getEmployeePage")
    public Page<Employee> getEmployeePage(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page,6);
        return employeeService.getEmployeePage(search, pageable);
    }

    @GetMapping("/findEmployeeById")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam Long employeeId) {

        Employee employee = employeeService.findEmployeeById(employeeId);

        try {
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

    @PostMapping("/createEmployee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employeeRequest) {
        try {
            return ResponseEntity.ok(employeeService.createEmployee(employeeRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/updateEmployeeById")
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee updatedEmployee){
        try {
            employeeService.updateEmployeeById(updatedEmployee.getId(), updatedEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/deleteEmployeeById")
    public ResponseEntity<Void> deleteEmployeeById(@RequestParam Long employeeId){
        try {
            employeeService.deleteEmployeeById(employeeId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok().build();
    }



}
