package com.hobiniaina.paymybuddy.repository;

import com.hobiniaina.paymybuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findBySenderId(Integer senderId);
}
