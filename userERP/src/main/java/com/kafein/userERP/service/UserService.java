package com.kafein.userERP.service;

import com.kafein.userERP.model.User;
import com.kafein.userERP.repository.DepartmentRepository;
import com.kafein.userERP.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public User createUser(User userRequest){

        User newUser = new User();

        newUser.setFullName(userRequest.getFullName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setDepartment(userRequest.getDepartment());
        newUser.setRestDay(userRequest.getRestDay());

        return userRepository.save(newUser);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public void updateUserById(Long userId, User updatedUser){

        Optional<User> existingUserCheck = userRepository.findById(userId);

        if(existingUserCheck.isEmpty()){
            throw new IllegalArgumentException("User with id " + userId + " not found.");
        }

        User existingUser = existingUserCheck.get();

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRestDay(updatedUser.getRestDay());
        existingUser.setDepartment(updatedUser.getDepartment());

        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUserById(Long userId){

        Optional<User> existingUserCheck = userRepository.findById(userId);

        if(existingUserCheck.isEmpty()){
            throw new IllegalArgumentException("User with id" + userId + "is not found.");
        }

        User existingUser = existingUserCheck.get();

        userRepository.deleteById(existingUser.getId());
    }

    public User findUserById(Long userId){
        Optional<User> existingUserCheck = userRepository.findById(userId);

        if(existingUserCheck.isEmpty()){
            throw new IllegalArgumentException("User with id" + userId + "is not found.");
        }
        return existingUserCheck.get();
    }


}
