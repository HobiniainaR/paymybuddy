package com.hobiniaina.paymybuddy.repository;

import com.hobiniaina.paymybuddy.model.Connection;
import com.hobiniaina.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
    List<Connection> findByOrigin(User origin);
}
