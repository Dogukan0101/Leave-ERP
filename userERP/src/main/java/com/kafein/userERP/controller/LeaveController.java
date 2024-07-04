package com.kafein.userERP.controller;

import com.kafein.userERP.model.Leave;
import com.kafein.userERP.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/getAllLeaves")
    public ResponseEntity<List<Leave>> getAllLeaves(){

        List<Leave> leaves = leaveService.getAllLeaves();

        try{
            return ResponseEntity.ok(leaves);
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }

}
