package com.librarymanagement.librarymanagement.services;

import com.librarymanagement.librarymanagement.dto.LoginDto;
import com.librarymanagement.librarymanagement.dto.RegistrationDTO;
import com.librarymanagement.librarymanagement.models.User;
import com.librarymanagement.librarymanagement.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;


    public void register(RegistrationDTO request) {

        userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new RuntimeException("Username already exists.");
        });
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email already exists.");
        });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }


    public User authenticateUser(LoginDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password.");
        }
        return user;
    }
}
