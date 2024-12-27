package com.app.Memora.authentication.repositories;

import com.app.Memora.authentication.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setFullName("Test User");
        userRepository.save(testUser);
    }

    @Test
    void findByEmail_ReturnsUser_WhenEmailExists() {
        // Act
        Optional<User> optionalUser = userRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(optionalUser.isPresent());
        assertEquals(testUser.getEmail(), optionalUser.get().getEmail());
        assertEquals(testUser.getFullName(), optionalUser.get().getFullName());
    }

    @Test
    void findByEmail_ReturnsEmpty_WhenEmailDoesNotExist() {
        // Act
        Optional<User> optionalUser = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void save_PersistsUser() {
        // Arrange
        User newUser = new User();
        newUser.setEmail("new@example.com");
        newUser.setPassword("newpassword");
        newUser.setFullName("New User");

        // Act
        User savedUser = userRepository.save(newUser);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals("new@example.com", savedUser.getEmail());
        assertEquals("New User", savedUser.getFullName());
    }

    @Test
    void delete_RemovesUser() {
        // Act
        userRepository.delete(testUser);

        // Assert
        Optional<User> optionalUser = userRepository.findById(testUser.getId());
        assertFalse(optionalUser.isPresent());
    }
}