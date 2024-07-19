package com.kafein.userERP.service;

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

    //@CachePut(cacheNames = "departments", key = "#result.id")
    public Department createDepartment(Department departmentRequest){

        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setNumOfEmployees(0);
        return departmentRepository.save(department);
    }
    //@Cacheable(cacheNames = "departments")
    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public List<DepartmentOptionsDTO> getAllDepartmentsForOptions(){
        return departmentRepository.getAllDepartmentOptions();
    }

    public Page<Department> getDepartmentPage(int page){
        Pageable pageable = PageRequest.of(page,6);
        return departmentRepository.findAll(pageable);
    }

    @Transactional
    //@CachePut(cacheNames = "departments", key = "#departmentId")
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
    //@CacheEvict(cacheNames = "departments", key = "#departmentId")
    public void deleteDepartmentById(Long departmentId){

        Optional<Department> existingDepartmentCheck = departmentRepository.findById(departmentId);

        if(existingDepartmentCheck.isEmpty()){
            throw new IllegalArgumentException("Department with id" + departmentId + "is not found.");
        }

        Department existingDepartment = existingDepartmentCheck.get();

        departmentRepository.deleteById(existingDepartment.getId());
    }

    //@CacheEvict(cacheNames = "departments", allEntries = true)
    public void calculateDepartmentEmployeeNumber(){
        List<Object[]> results = departmentRepository.getDepartmentEmployeeCounts();

        for(Object[] result: results){

            Long departmentId = (Long) result[0];
            Long employeeNumber = (Long) result[1];

            Optional<Department> departmentCheck = departmentRepository.findById(departmentId);
            if(departmentCheck.isEmpty()){
                throw new NoSuchElementException("Department with id " + departmentId + " is not found.");
            }
            Department department = departmentCheck.get();
            department.setNumOfEmployees(employeeNumber.intValue());

            departmentRepository.save(department);
        }
    }

}