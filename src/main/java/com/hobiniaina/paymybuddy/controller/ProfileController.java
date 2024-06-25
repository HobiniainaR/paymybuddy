package com.hobiniaina.paymybuddy.controller;

import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public String getProfilePage(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping
    public String updateProfile(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {
        User currentUser = userService.getCurrentUser();
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        userService.updateUser(currentUser);
        model.addAttribute("user", currentUser);
        model.addAttribute("success", "Profil mis à jour avec succès !");
        return "profile";
    }
}
