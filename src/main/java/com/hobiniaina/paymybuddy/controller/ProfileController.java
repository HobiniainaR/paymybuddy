package com.hobiniaina.paymybuddy.controller;

import com.hobiniaina.paymybuddy.dto.UserDTO;
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
        UserDTO userDTO = new UserDTO(currentUser.getUsername(), currentUser.getEmail(), currentUser.getPassword());
        model.addAttribute("user", userDTO);
        return "profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute UserDTO user, Model model) {
        try {
            userService.updateUserProfile(user);
            model.addAttribute("user", user);
            model.addAttribute("success", "Profil mis à jour avec succès !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour du profil.");
        }
        return "profile";
    }
}
