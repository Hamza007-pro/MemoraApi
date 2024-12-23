package com.app.Memora.content.dtos;

import com.app.Memora.answer.dtos.AnswerDTO;
import com.app.Memora.question.dtos.QuestionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContentReadDTOTest {

    private ContentReadDTO dto;
    private LocalDateTime testDateTime;
    private QuestionDTO testQuestion;
    private AnswerDTO testAnswer;

    @BeforeEach
    void setUp() {
        dto = new ContentReadDTO();
        testDateTime = LocalDateTime.of(2024, 12, 23, 13, 17, 30);

        testQuestion = new QuestionDTO();
        testQuestion.setId(1L);
        testQuestion.setContent("Test Question");

        testAnswer = new AnswerDTO();
        testAnswer.setId(1L);
        testAnswer.setContent("Test Answer");
    }

    @Test
    void testContentReadDTO_Creation() {
        // Act
        dto.setId(1L);
        dto.setImage("test-image.jpg");
        dto.setDateCreated(testDateTime);
        dto.setDateModified(testDateTime.plusHours(1));
        dto.setQuestion(testQuestion);
        dto.setAnswer(testAnswer);

        // Assert
        assertEquals(1L, dto.getId());
        assertEquals("test-image.jpg", dto.getImage());
        assertEquals(testDateTime, dto.getDateCreated());
        assertEquals(testDateTime.plusHours(1), dto.getDateModified());
        assertEquals(testQuestion, dto.getQuestion());
        assertEquals(testAnswer, dto.getAnswer());
    }

    @Test
    void testContentReadDTO_NoArgsConstructor() {
        // Arrange & Act
        ContentReadDTO newDto = new ContentReadDTO();

        // Assert
        assertNull(newDto.getId());
        assertNull(newDto.getImage());
        assertNull(newDto.getDateCreated());
        assertNull(newDto.getDateModified());
        assertNull(newDto.getQuestion());
        assertNull(newDto.getAnswer());
    }

    @Test
    void testContentReadDTO_ToString() {
        // Arrange
        dto.setId(1L);
        dto.setImage("test-image.jpg");
        dto.setQuestion(testQuestion);
        dto.setAnswer(testAnswer);

        // Act
        String toString = dto.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("image=test-image.jpg"));
        assertTrue(toString.contains("question=" + testQuestion.toString()));
        assertTrue(toString.contains("answer=" + testAnswer.toString()));
    }

    @Test
    void testContentReadDTO_EqualsAndHashCode() {
        // Arrange
        ContentReadDTO dto1 = new ContentReadDTO();
        dto1.setId(1L);
        dto1.setImage("test-image.jpg");
        dto1.setQuestion(testQuestion);
        dto1.setAnswer(testAnswer);

        ContentReadDTO dto2 = new ContentReadDTO();
        dto2.setId(1L);
        dto2.setImage("test-image.jpg");
        dto2.setQuestion(testQuestion);
        dto2.setAnswer(testAnswer);

        ContentReadDTO dto3 = new ContentReadDTO();
        dto3.setId(2L);
        dto3.setImage("different-image.jpg");

        // Assert
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testContentReadDTO_DateHandling() {
        // Arrange
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime modified = created.plusHours(1);

        // Act
        dto.setDateCreated(created);
        dto.setDateModified(modified);

        // Assert
        assertEquals(created, dto.getDateCreated());
        assertEquals(modified, dto.getDateModified());
        assertTrue(dto.getDateModified().isAfter(dto.getDateCreated()));
    }

    @Test
    void testContentReadDTO_WithNullNestedObjects() {
        // Arrange & Act
        dto.setId(1L);
        dto.setImage("test-image.jpg");
        dto.setQuestion(null);
        dto.setAnswer(null);

        // Assert
        assertNotNull(dto.getId());
        assertNotNull(dto.getImage());
        assertNull(dto.getQuestion());
        assertNull(dto.getAnswer());
    }

    @Test
    void testContentReadDTO_WithEmptyImage() {
        // Act
        dto.setImage("");

        // Assert
        assertEquals("", dto.getImage());
    }

    @Test
    void testContentReadDTO_WithNullImage() {
        // Act
        dto.setImage(null);

        // Assert
        assertNull(dto.getImage());
    }
}