package com.kafein.userERP.repository;

import com.kafein.userERP.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    void deleteUserById(Long userId);

    Optional<User> findUserById(Long userId);

}
