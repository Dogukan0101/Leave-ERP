package com.kafein.userERP.controller;

import com.kafein.userERP.model.Leave;
import com.kafein.userERP.model.User;
import com.kafein.userERP.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getLeavePage")
    public ResponseEntity<Page<Leave>> getLeavePage(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<Leave> leavePage = leaveService.getLeavePage(search, PageRequest.of(page,6));
        try {
            return ResponseEntity.ok(leavePage);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

    @PostMapping("/createLeave")
    public ResponseEntity<Void> createLeave(@RequestBody Leave leaveRequest){
        try {
            leaveService.createLeave(leaveRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    };

    @PostMapping("/updateLeaveById")
    public ResponseEntity<Void> updateLeaveById(@RequestBody Leave leaveRequest){
        try {
            leaveService.updateLeaveById(leaveRequest.getId(),leaveRequest);
        } catch (Exception e) {
            if("Date Conflict".equals(e.getMessage())){
                return ResponseEntity.status(409).build();
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    };

    @DeleteMapping("/deleteLeaveById")
    public ResponseEntity<Void> deleteLeaveById(@RequestParam Long leaveId){
        try {
            leaveService.deleteLeaveById(leaveId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getLeaveById")
    public ResponseEntity<Leave> getLeaveById(@RequestParam Long leaveId){

        Leave leave = leaveService.findLeaveById(leaveId);
        try{
            return ResponseEntity.ok(leave);
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }

}
