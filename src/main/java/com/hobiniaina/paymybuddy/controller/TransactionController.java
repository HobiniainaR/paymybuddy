package com.hobiniaina.paymybuddy.controller;

import com.hobiniaina.paymybuddy.dto.TransactionDTO;
import com.hobiniaina.paymybuddy.model.Connection;
import com.hobiniaina.paymybuddy.model.Transaction;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.service.ConnectionService;
import com.hobiniaina.paymybuddy.service.TransactionService;
import com.hobiniaina.paymybuddy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/transfers")
public class TransactionController {

    private final TransactionService transactionService;
    private final ConnectionService connectionService;
    private final UserService userService;
    @GetMapping("/transfer")
    public String getTransferPage( Model model) {
        User user =  userService.getCurrentUser();
        List<Connection> relations = connectionService.getConnectionsByUser(user);
        model.addAttribute("transfers", transactionService.getAllTransactionsByUser(user));
        model.addAttribute("newTransfer", new TransactionDTO());
        model.addAttribute("relations", relations);
        model.addAttribute("balance", user.getBalance());
        return "transfer";
    }

    @PostMapping("/transfer")
    public String postTransfer(  TransactionDTO transactionDTO) {
        User user = userService.getCurrentUser();
        transactionService.createAndSaveTransaction(user.getId(), transactionDTO);

        return  "redirect:/transfer";
    }
}