package com.kafein.userERP.controller;

import com.kafein.userERP.model.User;
import com.kafein.userERP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){

        List<User> users = userService.getAllUsers();

        try {
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

   @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User userRequest){
        try{
            return ResponseEntity.ok(userService.createUser(userRequest));
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
       }
   }


}
