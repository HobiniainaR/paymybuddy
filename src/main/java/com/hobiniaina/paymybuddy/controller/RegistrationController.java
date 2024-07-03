package com.hobiniaina.paymybuddy.controller;

import com.hobiniaina.paymybuddy.dto.UserDTO;
import com.hobiniaina.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser( @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        userService.register(userDTO);
        return "redirect:/register?success";
    }
}

