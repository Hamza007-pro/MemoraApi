package com.app.Memora.store.dtos;

import com.app.Memora.deck.dtos.DeckReadDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoreReadDTOTest {

    private StoreReadDTO dto;
    private LocalDateTime testDateTime;
    private List<DeckReadDTO> testDecks;

    @BeforeEach
    void setUp() {
        dto = new StoreReadDTO();
        testDateTime = LocalDateTime.of(2024, 12, 23, 13, 31, 19);

        // Initialize test decks
        DeckReadDTO deck1 = new DeckReadDTO();
        deck1.setId(1L);
        deck1.setName("Test Deck 1");

        DeckReadDTO deck2 = new DeckReadDTO();
        deck2.setId(2L);
        deck2.setName("Test Deck 2");

        testDecks = Arrays.asList(deck1, deck2);
    }

    @Test
    void testStoreReadDTO_Creation() {
        // Act
        dto.setId(1L);
        dto.setName("Test Store");
        dto.setDescription("Test Description");
        dto.setCreatedAt(testDateTime);
        dto.setUpdatedAt(testDateTime.plusHours(1));
        dto.setDecks(testDecks);

        // Assert
        assertEquals(1L, dto.getId());
        assertEquals("Test Store", dto.getName());
        assertEquals("Test Description", dto.getDescription());
        assertEquals(testDateTime, dto.getCreatedAt());
        assertEquals(testDateTime.plusHours(1), dto.getUpdatedAt());
        assertEquals(testDecks, dto.getDecks());
        assertEquals(2, dto.getDecks().size());
    }

    @Test
    void testStoreReadDTO_NoArgsConstructor() {
        // Arrange & Act
        StoreReadDTO newDto = new StoreReadDTO();

        // Assert
        assertNull(newDto.getId());
        assertNull(newDto.getName());
        assertNull(newDto.getDescription());
        assertNull(newDto.getCreatedAt());
        assertNull(newDto.getUpdatedAt());
        assertNull(newDto.getDecks());
    }

    @Test
    void testStoreReadDTO_ToString() {
        // Arrange
        dto.setId(1L);
        dto.setName("Test Store");
        dto.setDescription("Test Description");
        dto.setDecks(testDecks);

        // Act
        String toString = dto.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=Test Store"));
        assertTrue(toString.contains("description=Test Description"));
        assertTrue(toString.contains("decks="));
    }

    @Test
    void testStoreReadDTO_EqualsAndHashCode() {
        // Arrange
        StoreReadDTO dto1 = new StoreReadDTO();
        dto1.setId(1L);
        dto1.setName("Test Store");
        dto1.setDescription("Test Description");
        dto1.setCreatedAt(testDateTime);
        dto1.setDecks(testDecks);

        StoreReadDTO dto2 = new StoreReadDTO();
        dto2.setId(1L);
        dto2.setName("Test Store");
        dto2.setDescription("Test Description");
        dto2.setCreatedAt(testDateTime);
        dto2.setDecks(testDecks);

        StoreReadDTO dto3 = new StoreReadDTO();
        dto3.setId(2L);
        dto3.setName("Different Store");
        dto3.setDescription("Different Description");

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testStoreReadDTO_WithEmptyDecks() {
        // Arrange & Act
        dto.setId(1L);
        dto.setName("Test Store");
        dto.setDecks(new ArrayList<>());

        // Assert
        assertNotNull(dto.getDecks());
        assertTrue(dto.getDecks().isEmpty());
    }

    @Test
    void testStoreReadDTO_WithNullDecks() {
        // Arrange & Act
        dto.setId(1L);
        dto.setName("Test Store");
        dto.setDecks(null);

        // Assert
        assertNull(dto.getDecks());
    }

    @Test
    void testStoreReadDTO_DateHandling() {
        // Arrange
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = created.plusHours(1);

        // Act
        dto.setCreatedAt(created);
        dto.setUpdatedAt(updated);

        // Assert
        assertEquals(created, dto.getCreatedAt());
        assertEquals(updated, dto.getUpdatedAt());
        assertTrue(dto.getUpdatedAt().isAfter(dto.getCreatedAt()));
    }

    @Test
    void testStoreReadDTO_WithEmptyStrings() {
        // Act
        dto.setName("");
        dto.setDescription("");

        // Assert
        assertEquals("", dto.getName());
        assertEquals("", dto.getDescription());
    }

    @Test
    void testStoreReadDTO_WithNullStrings() {
        // Act
        dto.setName(null);
        dto.setDescription(null);

        // Assert
        assertNull(dto.getName());
        assertNull(dto.getDescription());
    }
}