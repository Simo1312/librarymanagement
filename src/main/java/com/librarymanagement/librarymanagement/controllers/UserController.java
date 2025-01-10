package com.librarymanagement.librarymanagement.controllers;

import com.librarymanagement.librarymanagement.dto.LoginDto;
import com.librarymanagement.librarymanagement.dto.RegistrationDTO;
import com.librarymanagement.librarymanagement.models.User;
import com.librarymanagement.librarymanagement.util.JwtUtil;
import com.librarymanagement.librarymanagement.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto request) {
        User user = userService.authenticateUser(request);
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getEmail());
        return ResponseEntity.ok(token);
    }
}
