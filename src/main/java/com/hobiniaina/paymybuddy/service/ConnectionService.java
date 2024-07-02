package com.hobiniaina.paymybuddy.service;

import com.hobiniaina.paymybuddy.model.Connection;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    public List<Connection> getConnectionsByUser(User user) {
        return connectionRepository.findByOriginId(user.getId());
    }
}