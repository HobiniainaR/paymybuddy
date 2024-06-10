package com.hobiniaina.paymybuddy.controller;

import com.hobiniaina.paymybuddy.model.Transaction;
import com.hobiniaina.paymybuddy.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/transfers")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transfer")
    public String getTransferPage(Model model) {
        List<String> relations = Arrays.asList("Laure-Anne", "Clara", "Luc");
        model.addAttribute("transfers", transactionService.getAllTransactions());
        model.addAttribute("newTransfer", new Transaction());
        model.addAttribute("relations", relations);
        return "transfer";
    }

    @PostMapping("/transfer")
    public String postTransfer(@ModelAttribute Transaction newTransfer, Model model) {

        transactionService.saveTransaction(newTransfer);
        List<String> relations = Arrays.asList("Laure-Anne", "Clara", "Luc");
        model.addAttribute("transfers", transactionService.getAllTransactions());
        model.addAttribute("newTransfer", new Transaction());
        model.addAttribute("relations", relations);
        return "transfer";
    }
}
