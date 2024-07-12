package com.kafein.userERP.controller;

import com.kafein.userERP.dtos.LeaveDTO;
import com.kafein.userERP.model.Leave;
import com.kafein.userERP.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Void> createLeave(@RequestBody Leave leaveRequest){
        try {
            leaveService.createLeave(leaveRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    };

    @CrossOrigin
    @DeleteMapping("/deleteLeaveById")
    public ResponseEntity<Void> deleteLeaveById(@RequestParam Long leaveId){
        try {
            leaveService.deleteLeaveById(leaveId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok().build();
    }

}
