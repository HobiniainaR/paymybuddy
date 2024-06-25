package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.dto.TransactionDTO;
import com.hobiniaina.paymybuddy.model.Connection;
import com.hobiniaina.paymybuddy.model.Transaction;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.TransactionRepository;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private  TransactionRepository transactionRepository;
    @Autowired
    private  ConnectionService connectionService;
    @Autowired

    private  UserRepository userRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionsByUser(User user) {
        return transactionRepository.findBySenderId(user.getId());
    }

    public void createAndSaveTransaction(Integer userId, TransactionDTO transactionDTO)  {

        User sender = userRepository.findById(userId).orElseThrow();

        User receiver =userRepository.findById(transactionDTO.getReceiverId()).orElseThrow();

        Transaction newTransfer = new Transaction();
        newTransfer.setSender(sender);
        newTransfer.setReceiver(receiver);
        newTransfer.setDescription(transactionDTO.getDescription());
        newTransfer.setAmount(transactionDTO.getAmount());

        transactionRepository.save(newTransfer);
    }

    public List<Connection> getConnectionsByUser(User user) {
        return connectionService.getConnectionsByUser(user);
    }

}