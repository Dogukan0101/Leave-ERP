package com.kafein.leaveERP.service;

import com.kafein.leaveERP.model.Employee;
import com.kafein.leaveERP.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    @Transactional
    public Employee createEmployee(Employee employeeRequest) {
        Employee newEmployee = new Employee();
        newEmployee.setFullName(employeeRequest.getFullName());
        newEmployee.setEmail(employeeRequest.getEmail());
        newEmployee.setDepartment(employeeRequest.getDepartment());
        newEmployee.setRestDay(employeeRequest.getRestDay());

        return employeeRepository.save(newEmployee);
    }

    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public Page<Employee> getEmployeePage(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return employeeRepository.findAll(pageable);
        } else {
            return employeeRepository.findByFullNameContaining(search, pageable);
        }
    }

    @Transactional
    public void updateEmployeeById(Long employeeId, Employee updatedEmployee){

        Optional<Employee> existingEmployeeCheck = employeeRepository.findById(employeeId);

        if(existingEmployeeCheck.isEmpty()){
            throw new IllegalArgumentException("Employee with id " + employeeId + " not found.");
        }

        Employee existingEmployee = existingEmployeeCheck.get();

        existingEmployee.setFullName(updatedEmployee.getFullName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setRestDay(updatedEmployee.getRestDay());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());

        employeeRepository.save(existingEmployee);
    }


    @Transactional
    public void deleteEmployeeById(Long employeeId){

        Optional<Employee> existingEmployeeCheck = employeeRepository.findEmployeeById(employeeId);

        if(existingEmployeeCheck.isEmpty()){
            throw new IllegalArgumentException("Employee with id" + employeeId + "is not found.");
        }

        Employee existingEmployee = existingEmployeeCheck.get();

        employeeRepository.deleteEmployeeById(existingEmployee.getId());
    }

    public Employee findEmployeeById(Long employeeId){
        Optional<Employee> existingEmployeeCheck = employeeRepository.findById(employeeId);

        if(existingEmployeeCheck.isEmpty()){
            throw new IllegalArgumentException("Employee with id" + employeeId + "is not found.");
        }
        return existingEmployeeCheck.get();
    }

}
