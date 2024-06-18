package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User getCurrentUser() {

        return userRepository.findByUsername("currentUser");
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

}
