package com.app.Memora.card.dtos;

import com.app.Memora.enums.DifficultyLevel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardEditDTOTest {

    @Test
    void testCardEditDTO_Creation() {
        // Arrange
        CardEditDTO dto = new CardEditDTO();

        // Act
        dto.setId(1L);
        dto.setDifficultyLevel(DifficultyLevel.EASY);
        dto.setContentId(2L);

        // Assert
        assertEquals(1L, dto.getId());
        assertEquals(DifficultyLevel.EASY, dto.getDifficultyLevel());
        assertEquals(2L, dto.getContentId());
    }

    @Test
    void testCardEditDTO_NoArgsConstructor() {
        // Arrange & Act
        CardEditDTO dto = new CardEditDTO();

        // Assert
        assertNull(dto.getId());
        assertNull(dto.getDifficultyLevel());
        assertNull(dto.getContentId());
    }

    @Test
    void testCardEditDTO_ToString() {
        // Arrange
        CardEditDTO dto = new CardEditDTO();
        dto.setId(1L);
        dto.setDifficultyLevel(DifficultyLevel.MEDIUM);
        dto.setContentId(2L);

        // Act
        String toString = dto.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("difficultyLevel=MEDIUM"));
        assertTrue(toString.contains("contentId=2"));
    }

    @Test
    void testCardEditDTO_EqualsAndHashCode() {
        // Arrange
        CardEditDTO dto1 = new CardEditDTO();
        dto1.setId(1L);
        dto1.setDifficultyLevel(DifficultyLevel.HARD);
        dto1.setContentId(2L);

        CardEditDTO dto2 = new CardEditDTO();
        dto2.setId(1L);
        dto2.setDifficultyLevel(DifficultyLevel.HARD);
        dto2.setContentId(2L);

        CardEditDTO dto3 = new CardEditDTO();
        dto3.setId(2L);
        dto3.setDifficultyLevel(DifficultyLevel.EASY);
        dto3.setContentId(3L);

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}