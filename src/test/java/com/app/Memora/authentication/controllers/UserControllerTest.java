package com.app.Memora.authentication.controllers;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        // Préparation des données de test
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("encoded_password");

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setEmail("test2@example.com");
        secondUser.setPassword("encoded_password2");

        userList = Arrays.asList(testUser, secondUser);

        // Configuration du SecurityContext
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void authenticatedUser_ReturnsCurrentUser() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);

        // Act
        ResponseEntity<User> response = userController.authenticatedUser();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser.getId(), response.getBody().getId());
        assertEquals(testUser.getEmail(), response.getBody().getEmail());
        verify(authentication, times(1)).getPrincipal();
    }

    @Test
    void allUsers_ReturnsListOfUsers() {
        // Arrange
        when(userService.allUsers()).thenReturn(userList);

        // Act
        ResponseEntity<List<User>> response = userController.allUsers();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(userList, response.getBody());
        verify(userService, times(1)).allUsers();
    }

    @Test
    void authenticatedUser_WithNullAuthentication_ReturnsNull() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(null);

        // Act
        ResponseEntity<User> response = userController.authenticatedUser();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(authentication, times(1)).getPrincipal();
    }
}