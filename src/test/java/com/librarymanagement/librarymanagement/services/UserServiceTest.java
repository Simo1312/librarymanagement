package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.LoginDto;
import com.librarymanagement.librarymanagement.dto.RegistrationDTO;
import com.librarymanagement.librarymanagement.models.User;
import com.librarymanagement.librarymanagement.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserSuccessfully() {
        RegistrationDTO request = new RegistrationDTO();
        request.setUsername("testuser");
        request.setEmail("testuser@example.com");
        request.setPassword("password123");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        userService.register(request);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUsernameExists() {
        RegistrationDTO request = new RegistrationDTO();
        request.setUsername("testuser");
        request.setEmail("testuser@example.com");
        request.setPassword("password123");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.register(request));
        assertEquals("Username already exists.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        RegistrationDTO request = new RegistrationDTO();
        request.setUsername("testuser");
        request.setEmail("testuser@example.com");
        request.setPassword("password123");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.register(request));
        assertEquals("Email already exists.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldAuthenticateUserSuccessfully() {
        LoginDto request = new LoginDto();
        request.setUsername("testuser");
        request.setPassword("password123");

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(request.getPassword(), mockUser.getPassword())).thenReturn(true);

        User authenticatedUser = userService.authenticateUser(request);

        assertEquals(mockUser, authenticatedUser);
        verify(userRepository, times(1)).findByUsername(request.getUsername());
    }

    @Test
    void shouldThrowExceptionForInvalidUsername() {
        LoginDto request = new LoginDto();
        request.setUsername("invaliduser");
        request.setPassword("password123");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.authenticateUser(request));
        assertEquals("Invalid username or password.", exception.getMessage());

        verify(userRepository, times(1)).findByUsername(request.getUsername());
    }

    @Test
    void shouldThrowExceptionForInvalidPassword() {
        LoginDto request = new LoginDto();
        request.setUsername("testuser");
        request.setPassword("wrongpassword");

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(request.getPassword(), mockUser.getPassword())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.authenticateUser(request));
        assertEquals("Invalid username or password.", exception.getMessage());

        verify(userRepository, times(1)).findByUsername(request.getUsername());
    }
}

