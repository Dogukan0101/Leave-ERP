package com.kafein.userERP.service;

import com.kafein.userERP.model.Leave;
import com.kafein.userERP.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public List<Leave> getAllLeaves(){
        return leaveRepository.findAll();
    }

}
