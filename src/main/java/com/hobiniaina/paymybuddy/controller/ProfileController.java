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

    private Integer getCurrentUserId() {
        return 1;
    }

    @GetMapping
    public String getProfilePage(Model model) {
        Integer userId = getCurrentUserId();
        User currentUser = userService.findUserById(userId);
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute User user, Model model) {
        try {
            user.setId(getCurrentUserId());
            userService.updateUserProfile(user);
            model.addAttribute("user", user);
            model.addAttribute("success", "Profil mis à jour avec succès !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour du profil.");
        }
        return "profile";
    }
}
