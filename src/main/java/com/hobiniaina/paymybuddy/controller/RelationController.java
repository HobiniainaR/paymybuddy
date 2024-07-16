package com.hobiniaina.paymybuddy.controller;

import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.service.ConnectionService;
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

    @Autowired
    private ConnectionService connectionService;

    @GetMapping
    public String showAddRelationPage(Model model) {
        model.addAttribute("user", new User());
        return "ajouter-relation";
    }

    @PostMapping
    public String addRelation(@ModelAttribute("user") User user, Model model) {

        User userOrigin = userService.getCurrentUser();
        if (userOrigin != null) {
            try {
                connectionService.addConnection(userOrigin, user.getEmail());
            } catch (Exception e) {
                model.addAttribute("error", "Utilisateur  non trouvé");
                return "ajouter-relation";
            }

                return "redirect:/transfers/transfer";
            } else{
                model.addAttribute("error", "Utilisateur authentifié non trouvé");
                return "ajouter-relation";
            }

        }
    }

