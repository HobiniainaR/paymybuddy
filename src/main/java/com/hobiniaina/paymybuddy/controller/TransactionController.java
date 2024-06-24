package com.hobiniaina.paymybuddy.controller;

import com.hobiniaina.paymybuddy.model.Connection;
import com.hobiniaina.paymybuddy.model.Transaction;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.service.ConnectionService;
import com.hobiniaina.paymybuddy.service.TransactionService;
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

    @GetMapping("/transfer/{userId}")
    public String getTransferPage(@PathVariable Integer userId, Model model) {
        User user = new User();
        user.setId(userId);
        List<Connection> relations = connectionService.getConnectionsByUser(user);
        model.addAttribute("transfers", transactionService.getAllTransactionsByUser(user));
        model.addAttribute("newTransfer", new Transaction());
        model.addAttribute("relations", relations);
        return "transfer";
    }

    @PostMapping("/transfer/{userId}")
    public String postTransfer(@PathVariable Integer userId, @ModelAttribute Transaction newTransfer, Model model) {
        User user = new User();
        user.setId(userId);
        newTransfer.setSender(user);
        transactionService.saveTransaction(newTransfer);
        List<Connection> relations = connectionService.getConnectionsByUser(user);
        model.addAttribute("transfers", transactionService.getAllTransactionsByUser(user));
        model.addAttribute("newTransfer", new Transaction());
        model.addAttribute("relations", relations);
        return "transfer";
    }
}
