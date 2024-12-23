package com.app.Memora.categorie.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryEditDTOTest {

    @Test
    void testCategoryEditDTO_Creation() {
        // Arrange
        CategoryEditDTO dto = new CategoryEditDTO();

        // Act
        dto.setName("Test Category");

        // Assert
        assertEquals("Test Category", dto.getName());
    }

    @Test
    void testCategoryEditDTO_NoArgsConstructor() {
        // Arrange & Act
        CategoryEditDTO dto = new CategoryEditDTO();

        // Assert
        assertNull(dto.getName());
    }

    @Test
    void testCategoryEditDTO_ToString() {
        // Arrange
        CategoryEditDTO dto = new CategoryEditDTO();
        dto.setName("Test Category");

        // Act
        String toString = dto.toString();

        // Assert
        assertTrue(toString.contains("name=Test Category"));
    }

    @Test
    void testCategoryEditDTO_EqualsAndHashCode() {
        // Arrange
        CategoryEditDTO dto1 = new CategoryEditDTO();
        dto1.setName("Test Category");

        CategoryEditDTO dto2 = new CategoryEditDTO();
        dto2.setName("Test Category");

        CategoryEditDTO dto3 = new CategoryEditDTO();
        dto3.setName("Different Category");

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testCategoryEditDTO_WithEmptyName() {
        // Arrange
        CategoryEditDTO dto = new CategoryEditDTO();
        dto.setName("");

        // Assert
        assertEquals("", dto.getName());
    }

    @Test
    void testCategoryEditDTO_WithNullName() {
        // Arrange
        CategoryEditDTO dto = new CategoryEditDTO();

        // Act
        dto.setName(null);

        // Assert
        assertNull(dto.getName());
    }

    @Test
    void testCategoryEditDTO_WithSpecialCharacters() {
        // Arrange
        CategoryEditDTO dto = new CategoryEditDTO();
        String nameWithSpecialChars = "Test@Category#123!";

        // Act
        dto.setName(nameWithSpecialChars);

        // Assert
        assertEquals(nameWithSpecialChars, dto.getName());
    }

    @Test
    void testCategoryEditDTO_CompareWithDifferentObject() {
        // Arrange
        CategoryEditDTO dto = new CategoryEditDTO();
        dto.setName("Test Category");
        Object otherObject = new Object();

        // Assert
        assertNotEquals(dto, otherObject);
    }
}