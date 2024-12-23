package com.app.Memora.authentication.controllers;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.entities.dtos.LoginResponseDto;
import com.app.Memora.authentication.entities.dtos.LoginUserDto;
import com.app.Memora.authentication.entities.dtos.RegisterUserDto;
import com.app.Memora.authentication.services.AuthenticationService;
import com.app.Memora.authentication.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private RegisterUserDto registerUserDto;
    private LoginUserDto loginUserDto;
    private User user;

    @BeforeEach
    void setUp() {
        // Préparation des données de test
        registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("test@example.com");
        registerUserDto.setPassword("password123");

        loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("test@example.com");
        loginUserDto.setPassword("password123");

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encoded_password");
    }

    @Test
    void register_WithValidData_ReturnsCreatedUser() {
        // Arrange
        when(authenticationService.signup(any(RegisterUserDto.class))).thenReturn(user);

        // Act
        ResponseEntity<User> response = authenticationController.register(registerUserDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getId(), response.getBody().getId());
        assertEquals(user.getEmail(), response.getBody().getEmail());
        verify(authenticationService, times(1)).signup(registerUserDto);
    }

    @Test
    void authenticate_WithValidCredentials_ReturnsLoginResponse() {
        // Arrange
        String token = "jwt_token";
        long expirationTime = 3600L;

        when(authenticationService.authenticate(any(LoginUserDto.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn(token);
        when(jwtService.getExpirationTime()).thenReturn(expirationTime);

        // Act
        ResponseEntity<LoginResponseDto> response = authenticationController.authenticate(loginUserDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(token, response.getBody().getToken());
        assertEquals(user, response.getBody().getUser());
        assertEquals(expirationTime, response.getBody().getExpiresIn());

        verify(authenticationService, times(1)).authenticate(loginUserDto);
        verify(jwtService, times(1)).generateToken(user);
        verify(jwtService, times(1)).getExpirationTime();
    }
}