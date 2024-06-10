package com.hobiniaina.paymybuddy.repository;

import com.hobiniaina.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
