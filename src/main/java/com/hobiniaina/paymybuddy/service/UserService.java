package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.dto.UserDTO;
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

        return userRepository.findById(1).orElseThrow();
    }
      public void updateUserProfile(UserDTO userDTO) {
        User currentUser = getCurrentUser();
        currentUser.setUsername(userDTO.getUsername());
        currentUser.setEmail(userDTO.getEmail());
        currentUser.setPassword(userDTO.getPassword());
        userRepository.save(currentUser);
    }
    public void register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setBalance(0.0);
        userRepository.save(user);
    }
}
