package com.app.Memora.store.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StoreCreationDTOTest {

    @Test
    void testStoreCreationDTO_Creation() {
        // Arrange
        StoreCreationDTO dto = new StoreCreationDTO();

        // Act
        dto.setName("Test Store");
        dto.setDescription("Test Description");

        // Assert
        assertEquals("Test Store", dto.getName());
        assertEquals("Test Description", dto.getDescription());
    }

    @Test
    void testStoreCreationDTO_NoArgsConstructor() {
        // Arrange & Act
        StoreCreationDTO dto = new StoreCreationDTO();

        // Assert
        assertNull(dto.getName());
        assertNull(dto.getDescription());
    }

    @Test
    void testStoreCreationDTO_ToString() {
        // Arrange
        StoreCreationDTO dto = new StoreCreationDTO();
        dto.setName("Test Store");
        dto.setDescription("Test Description");

        // Act
        String toString = dto.toString();

        // Assert
        assertTrue(toString.contains("name=Test Store"));
        assertTrue(toString.contains("description=Test Description"));
    }

    @Test
    void testStoreCreationDTO_EqualsAndHashCode() {
        // Arrange
        StoreCreationDTO dto1 = new StoreCreationDTO();
        dto1.setName("Test Store");
        dto1.setDescription("Test Description");

        StoreCreationDTO dto2 = new StoreCreationDTO();
        dto2.setName("Test Store");
        dto2.setDescription("Test Description");

        StoreCreationDTO dto3 = new StoreCreationDTO();
        dto3.setName("Different Store");
        dto3.setDescription("Different Description");

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testStoreCreationDTO_WithEmptyValues() {
        // Arrange
        StoreCreationDTO dto = new StoreCreationDTO();
        dto.setName("");
        dto.setDescription("");

        // Assert
        assertEquals("", dto.getName());
        assertEquals("", dto.getDescription());
    }

    @Test
    void testStoreCreationDTO_WithNullValues() {
        // Arrange
        StoreCreationDTO dto = new StoreCreationDTO();

        // Act
        dto.setName(null);
        dto.setDescription(null);

        // Assert
        assertNull(dto.getName());
        assertNull(dto.getDescription());
    }
}