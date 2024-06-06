package com.hobiniaina.paymybuddy.controller;

import com.hobiniaina.paymybuddy.model.Transaction;

import com.hobiniaina.paymybuddy.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@AllArgsConstructor
@RequestMapping("/transfers")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transfer")
    public String getTransferPage(Model model) {
        model.addAttribute("transfers", transactionService.getAllTransactions());
        model.addAttribute("newTransfer", new Transaction());
        return "transfer";
    }

    @PostMapping("/transfer")
    public String postTransfer(@ModelAttribute Transaction newTransfer, Model model) {
        transactionService.saveTransaction(newTransfer);
        model.addAttribute("transfers", transactionService.getAllTransactions());
        model.addAttribute("newTransfer", new Transaction());
        return "transfer";
    }
}
