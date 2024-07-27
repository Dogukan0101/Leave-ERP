package com.kafein.userERP.service;

import com.kafein.userERP.dtos.DepartmentEmployeeCountDTO;
import com.kafein.userERP.dtos.DepartmentOptionsDTO;
import com.kafein.userERP.model.Department;
import com.kafein.userERP.model.User;
import com.kafein.userERP.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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