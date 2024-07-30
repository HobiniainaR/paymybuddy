package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        String currentUserEmail = authentication.getName();
        return userRepository.findByEmail(currentUserEmail);
    }

    public void updateUserProfile(User user) {

        User existingUser = userRepository.findByEmail(user.getEmail());

        existingUser.setUsername(user.getUsername());

        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(existingUser);
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public void register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setBalance(0.0);
        userRepository.save(user);
    }
}
