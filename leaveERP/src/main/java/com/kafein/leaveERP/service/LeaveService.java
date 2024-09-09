package com.kafein.leaveERP.service;

import com.kafein.leaveERP.model.Employee;
import com.kafein.leaveERP.model.Leave;
import com.kafein.leaveERP.repository.LeaveRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeService employeeService;

    public Leave findLeaveById(Long leaveId){

        Optional<Leave> existingLeaveCheck = leaveRepository.findById(leaveId);

        if(existingLeaveCheck.isEmpty()){
            throw new NoSuchElementException("Leave with id " + leaveId + " is not found.");
        }

        return existingLeaveCheck.get();
    }

    public List<Leave> getAllLeaves(){
        return leaveRepository.findAll();
    }

    public Page<Leave> getLeavePage(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return leaveRepository.findAll(pageable);
        } else {
            return leaveRepository.findByEmployeeFullNameContaining(search, pageable);
        }
    }

    public boolean hasConflictingLeavesForAdd(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Leave> conflictingLeaves = leaveRepository.
                findConflictingLeaves(userId, startDate, endDate);
        return !conflictingLeaves.isEmpty();
    }

    public boolean hasConflictingLeavesForUpdate(Long userId, LocalDateTime startDate, LocalDateTime endDate, Long leaveId) {
        List<Leave> conflictingLeaves = leaveRepository.
                findConflictingLeavesExcludingCurrent(userId, startDate, endDate, leaveId);
        return !conflictingLeaves.isEmpty();
    }

    @Transactional
    public void createLeave(Leave leaveRequest){

        if(hasConflictingLeavesForAdd(leaveRequest.getEmployee().getId(),leaveRequest.getStartDate(),leaveRequest.getEndDate())){
            throw new IllegalStateException("The leave's start and end dates violate employee's other leaves.");
        }

        Leave leave = new Leave();

        leave.setDays(leaveRequest.getDays());
        leave.setStartDate(leaveRequest.getStartDate());
        leave.setEndDate(leaveRequest.getEndDate());
        leave.setEmployee(leaveRequest.getEmployee());

        leaveRepository.save(leave);

        Employee employee = employeeService.findEmployeeById(leaveRequest.getEmployee().getId());
        Long daysOfRest = employee.getRestDay() - leaveRequest.getDays();
        employee.setRestDay(daysOfRest);

        employeeService.updateEmployeeById(employee.getId(), employee);
    }

    @Transactional
    public void deleteLeaveById(Long leaveId){

        Leave existingLeave = findLeaveById(leaveId);

        Employee employee = employeeService.findEmployeeById(existingLeave.getEmployee().getId());

        Long currentLeaveDays = employee.getRestDay() + existingLeave.getDays();

        employee.setRestDay(currentLeaveDays);

        employeeService.updateEmployeeById(employee.getId(),employee);

        leaveRepository.deleteLeaveById(existingLeave.getId());
    }

    @Transactional
    public void updateLeaveById(Long leaveId, Leave updatedLeave){

        Optional<Leave> existingLeaveCheck = leaveRepository.findById(leaveId);

        if(existingLeaveCheck.isEmpty()){
            throw new NoSuchElementException("Leave with id " + leaveId + " is not found.");
        }

        if(hasConflictingLeavesForUpdate(updatedLeave.getEmployee().getId(),updatedLeave.getStartDate(),updatedLeave.getEndDate(),leaveId)){
            throw new IllegalStateException("Date Conflict");
        }

        Leave existingLeave = existingLeaveCheck.get();

        Employee existingEmployee = employeeService.findEmployeeById(existingLeave.getEmployee().getId());

        Long currentRestDay = existingEmployee.getRestDay() + existingLeave.getDays() - updatedLeave.getDays();
        existingEmployee.setRestDay(currentRestDay);

        existingLeave.setStartDate(updatedLeave.getStartDate());
        existingLeave.setEndDate(updatedLeave.getEndDate());
        existingLeave.setDays(updatedLeave.getDays());

        leaveRepository.save(existingLeave);
        employeeService.updateEmployeeById(existingEmployee.getId(),existingEmployee);
    }

}
