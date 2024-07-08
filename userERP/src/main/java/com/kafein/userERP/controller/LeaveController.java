package com.kafein.userERP.controller;

import com.kafein.userERP.dtos.LeaveDTO;
import com.kafein.userERP.model.Leave;
import com.kafein.userERP.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @CrossOrigin
    @GetMapping("/getAllLeaves")
    public ResponseEntity<List<Leave>> getAllLeaves(){

        List<Leave> leaves = leaveService.getAllLeaves();

        try{
            return ResponseEntity.ok(leaves);
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin
    @GetMapping("/getAllLeavesWithoutUserDetails")
    public ResponseEntity<List<LeaveDTO>> getAllLeavesWithoutUserDetails(){
        List<LeaveDTO> leaveDTOS = leaveService.getAllLeavesWithoutUserDetails();
        try{
            return ResponseEntity.ok(leaveDTOS);
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin
    @PostMapping("/createLeave")
    public ResponseEntity<Leave> createLeave(@RequestBody Leave leaveRequest){
        try {
            return ResponseEntity.ok(leaveService.createLeave(leaveRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
