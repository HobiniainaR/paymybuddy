package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User getCurrentUser() {

        User user = new User();
        user.setUsername("laure");
        user.setEmail("laure@domain.com");
        user.setPassword("laurePassword");
        return user;
    }



    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllRelations() {
               return userRepository.findAll();
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
