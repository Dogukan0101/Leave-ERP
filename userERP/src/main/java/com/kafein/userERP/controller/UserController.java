package com.kafein.userERP.controller;

import com.kafein.userERP.model.User;
import com.kafein.userERP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userService.getAllUsers();

        try {
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

    @CrossOrigin
    @GetMapping("/getUserPage")
    public Page<User> getUserPage(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page,6);
        return userService.getUserPage(search, pageable);
    }

    @CrossOrigin
    @GetMapping("/findUserById")
    public ResponseEntity<User> getUserById(@RequestParam Long userId) {

        User user = userService.findUserById(userId);

        try {
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    };

    @CrossOrigin
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User userRequest) {
        try {
            return ResponseEntity.ok(userService.createUser(userRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @CrossOrigin
    @PostMapping("/updateUserById")
    public ResponseEntity<User> updateUserById(@RequestBody User updatedUser){
        try {
            userService.updateUserById(updatedUser.getId(), updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(updatedUser);
    }

    @CrossOrigin
    @DeleteMapping("/deleteUserById")
    public ResponseEntity<Void> deleteUserById(@RequestParam Long userId){
        try {
            userService.deleteUserById(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok().build();
    }



}
