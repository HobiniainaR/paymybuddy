package com.hobiniaina.paymybuddy.controller;

import com.hobiniaina.paymybuddy.dto.UserDTO;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ajouter-relation")
public class RelationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showAddRelationPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "ajouter-relation";
    }

    @PostMapping
    public String addRelation(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        User existingUser = userService.findByEmail(userDTO.getEmail());
        if (existingUser != null) {
            return "redirect:/ajouter-relation?success";
        } else {
            model.addAttribute("error", "Utilisateur non trouvé");
            return "ajouter-relation";
        }
    }
}
