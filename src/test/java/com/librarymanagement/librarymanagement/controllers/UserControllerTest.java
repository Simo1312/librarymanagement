package com.librarymanagement.librarymanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.librarymanagement.dto.LoginDto;
import com.librarymanagement.librarymanagement.dto.RegistrationDTO;
import com.librarymanagement.librarymanagement.models.User;
import com.librarymanagement.librarymanagement.services.UserService;
import com.librarymanagement.librarymanagement.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(UserController.class)
class UserControllerTest {

    /*@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void register_Success() throws Exception {

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setUsername("testuser");
        registrationDTO.setEmail("test@example.com");
        registrationDTO.setPassword("password123");


        doNothing().when(userService).register(any(RegistrationDTO.class));


        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully."))
                .andDo(print());

        verify(userService).register(any(RegistrationDTO.class));
    }

    @Test
    void register_InvalidInput() throws Exception {

        RegistrationDTO registrationDTO = new RegistrationDTO();

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void register_EmailValidation() throws Exception {

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setUsername("testuser");
        registrationDTO.setEmail("invalid-email");
        registrationDTO.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void login_Success() throws Exception {

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("password123");

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setRole("USER");

        String expectedToken = "jwt.token.here";


        when(userService.authenticateUser(any(LoginDto.class))).thenReturn(user);
        when(jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getEmail()))
                .thenReturn(expectedToken);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedToken))
                .andDo(print());

        verify(userService).authenticateUser(any(LoginDto.class));
        verify(jwtUtil).generateToken(user.getUsername(), user.getRole(), user.getEmail());
    }

    @Test
    void login_InvalidCredentials() throws Exception {

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("wrongpassword");


        when(userService.authenticateUser(any(LoginDto.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));


        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @Test
    void login_InvalidInput() throws Exception {

        LoginDto loginDto = new LoginDto();

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void login_MissingUsername() throws Exception {

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void login_MissingPassword() throws Exception {

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testuser");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }*/
}
