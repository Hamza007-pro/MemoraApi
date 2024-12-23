package com.app.Memora.deck.dtos;

import com.app.Memora.card.dtos.CardReadDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckReadDTOTest {

    private DeckReadDTO dto;
    private LocalDateTime testDateTime;
    private List<CardReadDTO> testCards;
    private List<Long> testCategoryIds;
    private List<Long> testEnrollmentIds;

    @BeforeEach
    void setUp() {
        dto = new DeckReadDTO();
        testDateTime = LocalDateTime.of(2024, 12, 23, 13, 15, 47);
        testCards = new ArrayList<>();
        testCategoryIds = Arrays.asList(1L, 2L);
        testEnrollmentIds = Arrays.asList(3L, 4L);
    }

    @Test
    void testDeckReadDTO_Creation() {
        // Act
        dto.setId(1L);
        dto.setName("Test Deck");
        dto.setDescription("Test Description");
        dto.setImage("test-image.jpg");
        dto.setPublic(true);
        dto.setCreatedAt(testDateTime);
        dto.setUpdatedAt(testDateTime);
        dto.setCards(testCards);
        dto.setCategoryIds(testCategoryIds);
        dto.setEnrollmentIds(testEnrollmentIds);

        // Assert
        assertEquals(1L, dto.getId());
        assertEquals("Test Deck", dto.getName());
        assertEquals("Test Description", dto.getDescription());
        assertEquals("test-image.jpg", dto.getImage());
        assertTrue(dto.isPublic());
        assertEquals(testDateTime, dto.getCreatedAt());
        assertEquals(testDateTime, dto.getUpdatedAt());
        assertEquals(testCards, dto.getCards());
        assertEquals(testCategoryIds, dto.getCategoryIds());
        assertEquals(testEnrollmentIds, dto.getEnrollmentIds());
    }

    @Test
    void testDeckReadDTO_NoArgsConstructor() {
        // Arrange & Act
        DeckReadDTO newDto = new DeckReadDTO();

        // Assert
        assertNull(newDto.getId());
        assertNull(newDto.getName());
        assertNull(newDto.getDescription());
        assertNull(newDto.getImage());
        assertFalse(newDto.isPublic());
        assertNull(newDto.getCreatedAt());
        assertNull(newDto.getUpdatedAt());
        assertNull(newDto.getCards());
        assertNull(newDto.getCategoryIds());
        assertNull(newDto.getEnrollmentIds());
    }

    @Test
    void testDeckReadDTO_ToString() {
        // Arrange
        dto.setId(1L);
        dto.setName("Test Deck");
        dto.setDescription("Test Description");
        dto.setPublic(true);

        // Act
        String toString = dto.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=Test Deck"));
        assertTrue(toString.contains("description=Test Description"));
        assertTrue(toString.contains("public=true"));
    }

    @Test
    void testDeckReadDTO_EqualsAndHashCode() {
        // Arrange
        DeckReadDTO dto1 = new DeckReadDTO();
        dto1.setId(1L);
        dto1.setName("Test Deck");
        dto1.setCreatedAt(testDateTime);

        DeckReadDTO dto2 = new DeckReadDTO();
        dto2.setId(1L);
        dto2.setName("Test Deck");
        dto2.setCreatedAt(testDateTime);

        DeckReadDTO dto3 = new DeckReadDTO();
        dto3.setId(2L);
        dto3.setName("Different Deck");
        dto3.setCreatedAt(testDateTime.plusDays(1));

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testDeckReadDTO_WithEmptyLists() {
        // Arrange
        dto.setCards(new ArrayList<>());
        dto.setCategoryIds(new ArrayList<>());
        dto.setEnrollmentIds(new ArrayList<>());

        // Assert
        assertNotNull(dto.getCards());
        assertNotNull(dto.getCategoryIds());
        assertNotNull(dto.getEnrollmentIds());
        assertTrue(dto.getCards().isEmpty());
        assertTrue(dto.getCategoryIds().isEmpty());
        assertTrue(dto.getEnrollmentIds().isEmpty());
    }

    @Test
    void testDeckReadDTO_WithNullLists() {
        // Arrange
        dto.setCards(null);
        dto.setCategoryIds(null);
        dto.setEnrollmentIds(null);

        // Assert
        assertNull(dto.getCards());
        assertNull(dto.getCategoryIds());
        assertNull(dto.getEnrollmentIds());
    }

    @Test
    void testDeckReadDTO_DateHandling() {
        // Arrange
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now().plusHours(1);

        // Act
        dto.setCreatedAt(created);
        dto.setUpdatedAt(updated);

        // Assert
        assertEquals(created, dto.getCreatedAt());
        assertEquals(updated, dto.getUpdatedAt());
        assertTrue(dto.getUpdatedAt().isAfter(dto.getCreatedAt()));
    }

    @Test
    void testDeckReadDTO_WithCardsList() {
        // Arrange
        CardReadDTO card1 = new CardReadDTO();
        CardReadDTO card2 = new CardReadDTO();
        List<CardReadDTO> cards = Arrays.asList(card1, card2);

        // Act
        dto.setCards(cards);

        // Assert
        assertNotNull(dto.getCards());
        assertEquals(2, dto.getCards().size());
        assertTrue(dto.getCards().contains(card1));
        assertTrue(dto.getCards().contains(card2));
    }
}