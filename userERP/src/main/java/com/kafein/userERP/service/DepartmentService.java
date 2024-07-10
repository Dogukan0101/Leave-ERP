package com.kafein.userERP.service;

import com.kafein.userERP.dtos.DepartmentOptionsDTO;
import com.kafein.userERP.model.Department;
import com.kafein.userERP.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department departmentRequest){

        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setNumOfEmployees(0);
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public List<DepartmentOptionsDTO> getAllDepartmentsForOptions(){
        return departmentRepository.getAllDepartmentOptions();
    }

    @Transactional
    public void updateDepartmentById(Long departmentId, Department updatedDepartment){

        Optional<Department> existingDepartmentCheck = departmentRepository.findById(departmentId);

        if(existingDepartmentCheck.isEmpty()){
            throw new IllegalArgumentException("Department with id " + departmentId + " not found.");
        }

        Department existingDepartment = existingDepartmentCheck.get();

        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setNumOfEmployees(updatedDepartment.getNumOfEmployees());
        departmentRepository.save(existingDepartment);
    }

    @Transactional
    public void deleteDepartmentById(Long departmentId){

        Optional<Department> existingDepartmentCheck = departmentRepository.findById(departmentId);

        if(existingDepartmentCheck.isEmpty()){
            throw new IllegalArgumentException("Department with id" + departmentId + "is not found.");
        }

        Department existingDepartment = existingDepartmentCheck.get();

        departmentRepository.deleteById(existingDepartment.getId());
    }
}
