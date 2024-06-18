package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.model.Transaction;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionsByUser(User user) {
        return transactionRepository.findBySenderId(user.getId());
    }

    public Transaction getTransactionById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public void deleteTransaction(Integer id) {
        transactionRepository.deleteById(id);
    }
}
