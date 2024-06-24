package com.hobiniaina.paymybuddy.repository;

import com.hobiniaina.paymybuddy.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
    List<Connection> findByUserId(Integer userId);
}
