package com.kafein.userERP.repository;

import com.kafein.userERP.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
