package com.kafein.leaveERP.service;

import com.kafein.leaveERP.dtos.DepartmentEmployeeCountDTO;
import com.kafein.leaveERP.dtos.DepartmentOptionsDTO;
import com.kafein.leaveERP.model.Department;
import com.kafein.leaveERP.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public List<DepartmentEmployeeCountDTO> getDepartmentEmployeeCounts() {
        return departmentRepository.findDepartmentEmployeeCounts();
    }

    public List<DepartmentOptionsDTO> getAllDepartmentsForOptions(){
        return departmentRepository.getAllDepartmentOptions();
    }

    public Page<Department> getDepartmentPage(String search, Pageable pageable){
        if(search == null || search.isEmpty()) {
            return departmentRepository.findAll(pageable);
        } else {
            return departmentRepository.findByNameContaining(search, pageable);
        }
    }

    @Transactional
    public void updateDepartmentById(Long departmentId, Department updatedDepartment){

        Optional<Department> existingDepartmentCheck = departmentRepository.findById(departmentId);

        if(existingDepartmentCheck.isEmpty()){
            throw new IllegalArgumentException("Department with id " + departmentId + " not found.");
        }

        Department existingDepartment = existingDepartmentCheck.get();

        existingDepartment.setName(updatedDepartment.getName());
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