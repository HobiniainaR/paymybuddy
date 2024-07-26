package com.hobiniaina.paymybuddy.Service;

import com.hobiniaina.paymybuddy.model.User;
import com.hobiniaina.paymybuddy.repository.UserRepository;
import com.hobiniaina.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test@example.com");
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
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User currentUser = userService.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals(1, currentUser.getId());
    }

    @Test
    public void testGetCurrentUserNotAuthenticated() {
        when(authentication.isAuthenticated()).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getCurrentUser());
        assertEquals("Utilisateur non authentifié", exception.getMessage());
    }

    @Test
    public void testUpdateUserProfile() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setEmail("updated@example.com");
        updatedUser.setUsername("updateduser");
        updatedUser.setPassword("newpassword");

        userService.updateUserProfile(updatedUser);

        verify(userRepository, times(1)).save(user);
        assertEquals("updateduser", user.getUsername());
        assertEquals("updated@example.com", user.getEmail());
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    public void testUpdateUserProfileNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setEmail("updated@example.com");
        updatedUser.setUsername("updateduser");
        updatedUser.setPassword("newpassword");

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

    @Test
    public void testRegisterNewUser() {
        User newUser = new User();
        newUser.setId(2);
        newUser.setEmail("newuser@example.com");
        newUser.setUsername("newuser");
        newUser.setPassword("newpassword");

        when(userRepository.findByEmail("newuser@example.com")).thenReturn(null);
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");

        userService.register(newUser);

        verify(userRepository, times(1)).save(newUser);
        assertEquals(200.0, newUser.getBalance());
        assertEquals("encodedNewPassword", newUser.getPassword());
    }

    @Test
    public void testRegisterExistingUser() {
        User existingUser = new User();
        existingUser.setId(2);
        existingUser.setEmail("existing@example.com");
        existingUser.setUsername("existinguser");
        existingUser.setPassword("existingpassword");

        when(userRepository.findByEmail("existing@example.com")).thenReturn(existingUser);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.register(existingUser));
        assertEquals("Un utilisateur avec cet email existe déjà", exception.getMessage());
        verify(userRepository, times(0)).save(existingUser);
    }
}
