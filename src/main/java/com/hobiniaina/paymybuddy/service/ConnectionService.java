package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.model.Connection;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.ConnectionRepository;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    public List<Connection> getConnectionsByUser(User user) {
        return connectionRepository.findByOriginId(user.getId());
    }

    public void addConnection(User userOrigin, String email) {
        User targetUser = userRepository.findByEmail(email);
        if (targetUser != null) {
            Connection connection = new Connection();
            connection.setOrigin(userOrigin);
            connection.setUser(targetUser);
            connectionRepository.save(connection);
        } else {
            throw new IllegalArgumentException("Utilisateur avec l'email " + email + " non trouvé.");
        }
    }
}
