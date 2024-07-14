package com.kafein.userERP.service;

import com.kafein.userERP.model.Leave;
import com.kafein.userERP.model.User;
import com.kafein.userERP.repository.LeaveRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Leave> getAllLeaves(){
        return leaveRepository.findAll();
    }

    @Transactional
    public void createLeave(Leave leaveRequest){

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
    public void deleteLeaveById(Long leaveId){

        Leave existingLeave = findLeaveById(leaveId);

        User user = userService.findUserById(existingLeave.getUser().getId());

        Long currentLeaveDays = user.getRestDay() + existingLeave.getDays();

        user.setRestDay(currentLeaveDays);

        userService.updateUserById(user.getId(),user);

        leaveRepository.deleteLeaveById(existingLeave.getId());
    }


    @Transactional
    public void updateLeaveById(Long leaveId, Leave updatedLeave){

        Optional<Leave> existingLeaveCheck = leaveRepository.findById(leaveId);

        if(existingLeaveCheck.isEmpty()){
            throw new NoSuchElementException("Leave with id " + leaveId + " is not found.");
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





