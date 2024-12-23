package com.app.Memora.authentication.entities.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterUserDtoTest {

    private RegisterUserDto dto;

    @BeforeEach
    void setUp() {
        dto = new RegisterUserDto();
    }

    @Test
    void testRegisterUserDto_Creation() {
        // Arrange & Act
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setFullName("Test User");

        // Assert
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
        assertEquals("Test User", dto.getFullName());
    }

    @Test
    void testRegisterUserDto_NoArgsConstructor() {
        // Arrange & Act
        RegisterUserDto newDto = new RegisterUserDto();

        // Assert
        assertNull(newDto.getEmail());
        assertNull(newDto.getPassword());
        assertNull(newDto.getFullName());
    }

    @Test
    void testRegisterUserDto_WithEmptyValues() {
        // Arrange & Act
        dto.setEmail("");
        dto.setPassword("");
        dto.setFullName("");

        // Assert
        assertEquals("", dto.getEmail());
        assertEquals("", dto.getPassword());
        assertEquals("", dto.getFullName());
    }

    @Test
    void testRegisterUserDto_WithNullValues() {
        // Arrange & Act
        dto.setEmail(null);
        dto.setPassword(null);
        dto.setFullName(null);

        // Assert
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
        assertNull(dto.getFullName());
    }

    @Test
    void testRegisterUserDto_WithSpecialCharacters() {
        // Arrange
        String emailWithSpecialChars = "test.user+123@example.com";
        String passwordWithSpecialChars = "P@ssw0rd!#$";
        String nameWithSpecialChars = "O'Connor-Smith Jr.";

        // Act
        dto.setEmail(emailWithSpecialChars);
        dto.setPassword(passwordWithSpecialChars);
        dto.setFullName(nameWithSpecialChars);

        // Assert
        assertEquals(emailWithSpecialChars, dto.getEmail());
        assertEquals(passwordWithSpecialChars, dto.getPassword());
        assertEquals(nameWithSpecialChars, dto.getFullName());
    }

    @Test
    void testRegisterUserDto_WithLongValues() {
        // Arrange
        String longEmail = "very.long.email.address.that.might.be.too.long@really.long.domain.name.com";
        String longPassword = "ThisIsAVeryLongPasswordThatMightExceedTheMaximumAllowedLength123!@#";
        String longName = "This Is A Very Long Name That Might Exceed The Maximum Allowed Length For A Full Name Field";

        // Act
        dto.setEmail(longEmail);
        dto.setPassword(longPassword);
        dto.setFullName(longName);

        // Assert
        assertEquals(longEmail, dto.getEmail());
        assertEquals(longPassword, dto.getPassword());
        assertEquals(longName, dto.getFullName());
    }
}