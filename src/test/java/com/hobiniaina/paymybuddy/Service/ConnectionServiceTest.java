package com.hobiniaina.paymybuddy.Service;

import com.hobiniaina.paymybuddy.model.Connection;
import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.ConnectionRepository;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import com.hobiniaina.paymybuddy.service.ConnectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConnectionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConnectionRepository connectionRepository;

    @InjectMocks
    private ConnectionService connectionService;

    private User userOrigin;
    private User targetUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userOrigin = new User();
        userOrigin.setId(1);
        userOrigin.setEmail("origin@example.com");

        targetUser = new User();
        targetUser.setId(2);
        targetUser.setEmail("target@example.com");
    }

    @Test
    public void testAddConnectionSuccess() {
        when(userRepository.findByEmail("target@example.com")).thenReturn(targetUser);

        connectionService.addConnection(userOrigin, "target@example.com");

        verify(connectionRepository, times(1)).save(any(Connection.class));
    }

    @Test
    public void testAddConnectionUserNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> connectionService.addConnection(userOrigin, "unknown@example.com"));

        assertEquals("Utilisateur avec l'email unknown@example.com non trouvé.", exception.getMessage());
        verify(connectionRepository, times(0)).save(any(Connection.class));
    }

    @Test
    public void testGetConnectionsByUser() {
        Connection connection = new Connection();
        connection.setOrigin(userOrigin);
        connection.setUser(targetUser);

        when(connectionRepository.findByOriginId(userOrigin.getId())).thenReturn(List.of(connection));

        List<Connection> connections = connectionService.getConnectionsByUser(userOrigin);

        assertNotNull(connections);
        assertEquals(1, connections.size());
        assertEquals(targetUser, connections.get(0).getUser());
    }
}
