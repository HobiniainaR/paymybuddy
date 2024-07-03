package com.hobiniaina.paymybuddy.Service;

import com.hobiniaina.paymybuddy.dto.TransactionDTO;
import com.hobiniaina.paymybuddy.model.Transaction;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.TransactionRepository;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import com.hobiniaina.paymybuddy.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAndSaveTransaction_NullAmount() {

        Integer userId = 1;
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transactionService.createAndSaveTransaction(userId, transactionDTO));

        assertEquals("Le montant ne peut pas être nul.", exception.getMessage());
    }

    @Test
    public void testCreateAndSaveTransaction_NegativeOrZeroAmount() {

        Integer userId = 1;
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(0.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transactionService.createAndSaveTransaction(userId, transactionDTO));

        assertEquals("Le montant doit être supérieur à zéro.", exception.getMessage());
    }

    @Test
    public void testCreateAndSaveTransaction_SenderNotFound() {

        Integer userId = 1;
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(100.0);
        transactionDTO.setReceiverId(2);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> transactionService.createAndSaveTransaction(userId, transactionDTO));

        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    public void testCreateAndSaveTransaction_ReceiverNotFound() {

        Integer userId = 1;
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(100.0);
        transactionDTO.setReceiverId(2);

        User sender = new User();
        sender.setId(userId);
        sender.setBalance(200.0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(transactionDTO.getReceiverId())).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> transactionService.createAndSaveTransaction(userId, transactionDTO));

        assertEquals("Receiver not found", exception.getMessage());
    }

    @Test
    public void testCreateAndSaveTransaction_WithInsufficientBalance() {

        Integer userId = 1;
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(300.0);
        transactionDTO.setReceiverId(2);

        User sender = new User();
        sender.setId(userId);
        sender.setBalance(200.0);

        User receiver = new User();
        receiver.setId(transactionDTO.getReceiverId());

        when(userRepository.findById(userId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(transactionDTO.getReceiverId())).thenReturn(Optional.of(receiver));


        RuntimeException exception = assertThrows(RuntimeException.class, () -> transactionService.createAndSaveTransaction(userId, transactionDTO));

        assertEquals("Solde insuffisant.", exception.getMessage());
    }

    @Test
    public void testCreateAndSaveTransaction_WithValidData() {

        Integer userId = 1;
        Integer receiverId = 2;
        Double amount = 100.0;
        String description = "Test transaction";

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setReceiverId(receiverId);
        transactionDTO.setAmount(amount);
        transactionDTO.setDescription(description);

        User sender = new User();
        sender.setId(userId);
        sender.setBalance(200.0);

        User receiver = new User();
        receiver.setId(receiverId);
        receiver.setBalance(50.0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any())).thenAnswer(invocation -> {
            Transaction savedTransaction = invocation.getArgument(0);
            savedTransaction.setId(1);
            return savedTransaction;
        });


        assertDoesNotThrow(() -> transactionService.createAndSaveTransaction(userId, transactionDTO));


        assertEquals(sender.getBalance(), 100.0);
        assertEquals(receiver.getBalance(), 150.0);
        verify(userRepository, times(2)).save(any());
        verify(transactionRepository, times(1)).save(any());
    }

}

