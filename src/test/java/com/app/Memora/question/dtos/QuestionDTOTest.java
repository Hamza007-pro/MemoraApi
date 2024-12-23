package com.app.Memora.question.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestionDTOTest {

    private QuestionDTO dto;

    @BeforeEach
    void setUp() {
        dto = new QuestionDTO();
    }

    @Test
    void testQuestionDTO_Creation() {
        // Arrange & Act
        dto.setId(1L);
        dto.setContent("What is the capital of France?");

        // Assert
        assertEquals(1L, dto.getId());
        assertEquals("What is the capital of France?", dto.getContent());
    }

    @Test
    void testQuestionDTO_NoArgsConstructor() {
        // Arrange & Act
        QuestionDTO newDto = new QuestionDTO();

        // Assert
        assertNull(newDto.getId());
        assertNull(newDto.getContent());
    }

    @Test
    void testQuestionDTO_ToString() {
        // Arrange
        dto.setId(1L);
        dto.setContent("Test question?");

        // Act
        String toString = dto.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("content=Test question?"));
    }

    @Test
    void testQuestionDTO_EqualsAndHashCode() {
        // Arrange
        QuestionDTO dto1 = new QuestionDTO();
        dto1.setId(1L);
        dto1.setContent("Test question?");

        QuestionDTO dto2 = new QuestionDTO();
        dto2.setId(1L);
        dto2.setContent("Test question?");

        QuestionDTO dto3 = new QuestionDTO();
        dto3.setId(2L);
        dto3.setContent("Different question?");

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testQuestionDTO_WithNullContent() {
        // Act
        dto.setId(1L);
        dto.setContent(null);

        // Assert
        assertNotNull(dto.getId());
        assertNull(dto.getContent());
    }

    @Test
    void testQuestionDTO_WithEmptyContent() {
        // Act
        dto.setId(1L);
        dto.setContent("");

        // Assert
        assertNotNull(dto.getId());
        assertEquals("", dto.getContent());
    }

    @Test
    void testQuestionDTO_WithLongContent() {
        // Arrange
        String longContent = "This is a very long question that might contain multiple sentences. " +
                "It could be a complex question with lots of details and context. " +
                "We want to make sure the DTO can handle long content properly.";

        // Act
        dto.setId(1L);
        dto.setContent(longContent);

        // Assert
        assertEquals(longContent, dto.getContent());
    }

    @Test
    void testQuestionDTO_WithSpecialCharacters() {
        // Arrange
        String contentWithSpecialChars = "What is the meaning of '@#$%^&*()' in programming?";

        // Act
        dto.setId(1L);
        dto.setContent(contentWithSpecialChars);

        // Assert
        assertEquals(contentWithSpecialChars, dto.getContent());
    }
}