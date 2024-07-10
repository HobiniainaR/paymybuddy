package com.hobiniaina.paymybuddy.Service;

import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import com.hobiniaina.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
    }

    @Test
    public void testFindByEmail() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User foundUser = userService.findByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    public void testGetCurrentUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User currentUser = userService.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals(1, currentUser.getId());
    }

    @Test
    public void testGetCurrentUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getCurrentUser());

        assertEquals("Utilisateur non trouvé", exception.getMessage());
    }

    @Test
    public void testUpdateUserProfile() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setEmail("updated@example.com");
        updatedUser.setUsername("updateduser");
        updatedUser.setPassword("newpassword");

        userService.updateUserProfile(updatedUser);

        verify(userRepository, times(1)).save(user);
        assertEquals("updateduser", user.getUsername());
        assertEquals("updated@example.com", user.getEmail());
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void testUpdateUserProfileNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setEmail("updated@example.com");
        updatedUser.setUsername("updateduser");
        updatedUser.setPassword("newpassword");

        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUserProfile(updatedUser));

        assertEquals("Utilisateur non trouvé", exception.getMessage());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testFindUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(1);

        assertNotNull(foundUser);
        assertEquals(1, foundUser.getId());
    }

    @Test
    public void testFindUserByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.findUserById(1));

        assertEquals("Utilisateur non trouvé", exception.getMessage());
    }
}
