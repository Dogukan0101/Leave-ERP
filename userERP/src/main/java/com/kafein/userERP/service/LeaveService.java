package com.kafein.userERP.service;

import com.kafein.userERP.model.Leave;
import com.kafein.userERP.model.User;
import com.kafein.userERP.repository.LeaveRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private UserService userService;

    public Leave findLeaveById(Long leaveId){

        Optional<Leave> existingLeaveCheck = leaveRepository.findById(leaveId);

        if(existingLeaveCheck.isEmpty()){
            throw new NoSuchElementException("Leave with id " + leaveId + " is not found.");
        }

        return existingLeaveCheck.get();
    }

    //@Cacheable(cacheNames = "leaves")
    public List<Leave> getAllLeaves(){
        return leaveRepository.findAll();
    }

    public Page<Leave> getLeavePage(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return leaveRepository.findAll(pageable);
        } else {
            return leaveRepository.findByUserFullNameContaining(search, pageable);
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
    //@CachePut(cacheNames = "leaves", key = "#result.id")
    public void createLeave(Leave leaveRequest){

        if(hasConflictingLeavesForAdd(leaveRequest.getUser().getId(),leaveRequest.getStartDate(),leaveRequest.getEndDate())){
            throw new IllegalStateException("The leave's start and end dates violate user's other leaves.");
        }

        Leave leave = new Leave();

        leave.setDays(leaveRequest.getDays());
        leave.setStartDate(leaveRequest.getStartDate());
        leave.setEndDate(leaveRequest.getEndDate());
        leave.setUser(leaveRequest.getUser());

        leaveRepository.save(leave);

        User user = userService.findUserById(leaveRequest.getUser().getId());
        Long daysOfRest = user.getRestDay() - leaveRequest.getDays();
        user.setRestDay(daysOfRest);

        userService.updateUserById(user.getId(), user);
    }

    @Transactional
    //@CacheEvict(cacheNames = "leaves", key = "#leaveId")
    public void deleteLeaveById(Long leaveId){

        Leave existingLeave = findLeaveById(leaveId);

        User user = userService.findUserById(existingLeave.getUser().getId());

        Long currentLeaveDays = user.getRestDay() + existingLeave.getDays();

        user.setRestDay(currentLeaveDays);

        userService.updateUserById(user.getId(),user);

        leaveRepository.deleteLeaveById(existingLeave.getId());
    }

    @Transactional
    //@CachePut(cacheNames = "leaves", key = "#leaveId")
    public void updateLeaveById(Long leaveId, Leave updatedLeave){

        Optional<Leave> existingLeaveCheck = leaveRepository.findById(leaveId);

        if(existingLeaveCheck.isEmpty()){
            throw new NoSuchElementException("Leave with id " + leaveId + " is not found.");
        }

        if(hasConflictingLeavesForUpdate(updatedLeave.getUser().getId(),updatedLeave.getStartDate(),updatedLeave.getEndDate(),leaveId)){
            throw new IllegalStateException("Date Conflict");
        }

        Leave existingLeave = existingLeaveCheck.get();

        User existingUser = userService.findUserById(existingLeave.getUser().getId());

        Long currentRestDay = existingUser.getRestDay() + existingLeave.getDays() - updatedLeave.getDays();
        existingUser.setRestDay(currentRestDay);

        existingLeave.setStartDate(updatedLeave.getStartDate());
        existingLeave.setEndDate(updatedLeave.getEndDate());
        existingLeave.setDays(updatedLeave.getDays());

        leaveRepository.save(existingLeave);
        userService.updateUserById(existingUser.getId(),existingUser);
    }

}
