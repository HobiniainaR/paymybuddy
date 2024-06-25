package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.dto.TransactionDTO;
import com.hobiniaina.paymybuddy.model.Connection;
import com.hobiniaina.paymybuddy.model.Transaction;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private  TransactionRepository transactionRepository;
    private  ConnectionService connectionService;
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionsByUser(User user) {
        return transactionRepository.findBySenderId(user.getId());
    }

       public void createAndSaveTransaction(Integer userId, TransactionDTO transactionDTO) {
        User sender = new User();
        sender.setId(userId);

        User receiver = new User();
        receiver.setId(transactionDTO.getReceiver_id());

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
