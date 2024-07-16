package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.dto.TransactionDTO;
import com.hobiniaina.paymybuddy.model.Transaction;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.TransactionRepository;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;
    public List<Transaction> getAllTransactionsByUser(User user) {
        return transactionRepository.findBySenderId(user.getId());
    }
    @Transactional
    public void createAndSaveTransaction(Integer userId, TransactionDTO transactionDTO) {

         Double amount = transactionDTO.getAmount();
        if (amount == null) {
            throw new IllegalArgumentException("Le montant ne peut pas être nul.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à zéro.");
        }

        User sender = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(transactionDTO.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Solde insuffisant.");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);

        Transaction newTransfer = new Transaction();
        newTransfer.setSender(sender);
        newTransfer.setReceiver(receiver);
        newTransfer.setDescription(transactionDTO.getDescription());
        newTransfer.setAmount(amount);

        transactionRepository.save(newTransfer);
    }
}