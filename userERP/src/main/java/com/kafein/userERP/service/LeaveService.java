package com.kafein.userERP.service;

import com.kafein.userERP.dtos.LeaveDTO;
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

    public LeaveDTO findLeaveById(Long leaveId){

        Optional<LeaveDTO> existingLeaveCheck = leaveRepository.findLeaveById(leaveId);
        if(existingLeaveCheck.isEmpty()){
            throw new NoSuchElementException("Leave with id " + leaveId + " is not found.");
        }
        return existingLeaveCheck.get();
    }

    public List<Leave> getAllLeaves(){
        return leaveRepository.findAll();
    }

    public List<LeaveDTO> getAllLeavesWithoutUserDetails(){

        List<Leave> leaves = leaveRepository.findAll();
        List<LeaveDTO> leaveDTOS = new ArrayList<LeaveDTO>();

        for(Leave leave : leaves){
            LeaveDTO leaveDTO = new LeaveDTO();

            leaveDTO.setId(leave.getId());
            leaveDTO.setStartDate(leave.getStartDate());
            leaveDTO.setEndDate(leave.getEndDate());
            leaveDTO.setCreatedAt(leave.getCreatedAt());
            leaveDTO.setUserId(leave.getUser().getId());
            leaveDTO.setUserName(leave.getUser().getFullName());

            leaveDTOS.add(leaveDTO);
        }
        return leaveDTOS;
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

        Optional<LeaveDTO> existingLeaveCheck = leaveRepository.findLeaveById(leaveId);

        if(existingLeaveCheck.isEmpty()){
            throw new NoSuchElementException("Leave with id " + leaveId + " is not found." );
        }

        LeaveDTO existingLeave = existingLeaveCheck.get();

        User user = userService.findUserById(existingLeave.getUserId());

        Long currentLeave = user.getRestDay() + existingLeave.getDays();

        user.setRestDay(currentLeave);

        userService.updateUserById(user.getId(),user);

        leaveRepository.deleteLeaveById(leaveId);
    }

}
