package com.app.Memora.question.controllers;

import com.app.Memora.question.dtos.QuestionDTO;
import com.app.Memora.question.entities.Question;
import com.app.Memora.question.services.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    private Question question;
    private QuestionDTO questionDTO;

    @BeforeEach
    void setUp() {
        // Setup test question
        question = new Question();
        question.setId(1L);
        question.setContent("Test Question Content");

        // Setup question DTO
        questionDTO = new QuestionDTO();
        questionDTO.setId(1L);
        questionDTO.setContent("Test Question Content");
    }

    @Test
    void createQuestion_ReturnsCreatedQuestion() {
        // Arrange
        when(questionService.saveQuestion(any(Question.class))).thenReturn(question);

        // Act
        QuestionDTO result = questionController.createQuestion(questionDTO);

        // Assert
        assertNotNull(result);
        assertEquals(question.getId(), result.getId());
        assertEquals(question.getContent(), result.getContent());
        verify(questionService).saveQuestion(any(Question.class));
    }

    @Test
    void getQuestionById_WhenQuestionExists_ReturnsQuestion() {
        // Arrange
        when(questionService.getQuestionById(1L)).thenReturn(Optional.of(question));

        // Act
        ResponseEntity<QuestionDTO> response = questionController.getQuestionById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(question.getId(), response.getBody().getId());
        assertEquals(question.getContent(), response.getBody().getContent());
        verify(questionService).getQuestionById(1L);
    }

    @Test
    void getQuestionById_WhenQuestionDoesNotExist_ReturnsNotFound() {
        // Arrange
        when(questionService.getQuestionById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<QuestionDTO> response = questionController.getQuestionById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(questionService).getQuestionById(1L);
    }

    @Test
    void getAllQuestions_ReturnsListOfQuestions() {
        // Arrange
        List<Question> questions = Arrays.asList(question);
        when(questionService.getAllQuestions()).thenReturn(questions);

        // Act
        List<QuestionDTO> result = questionController.getAllQuestions();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(question.getId(), result.get(0).getId());
        assertEquals(question.getContent(), result.get(0).getContent());
        verify(questionService).getAllQuestions();
    }

    @Test
    void updateQuestion_ReturnsUpdatedQuestion() {
        // Arrange
        when(questionService.updateQuestion(eq(1L), any(Question.class))).thenReturn(question);

        // Act
        ResponseEntity<QuestionDTO> response = questionController.updateQuestion(1L, questionDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(question.getId(), response.getBody().getId());
        assertEquals(question.getContent(), response.getBody().getContent());
        verify(questionService).updateQuestion(eq(1L), any(Question.class));
    }

    @Test
    void deleteQuestion_ReturnsNoContent() {
        // Arrange
        doNothing().when(questionService).deleteQuestion(1L);

        // Act
        ResponseEntity<Void> response = questionController.deleteQuestion(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(questionService).deleteQuestion(1L);
    }

    @Test
    void updateQuestion_WithNonexistentId_DelegatesHandlingToService() {
        // Arrange
        when(questionService.updateQuestion(eq(999L), any(Question.class)))
                .thenThrow(new RuntimeException("Question not found"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                questionController.updateQuestion(999L, questionDTO)
        );
        assertEquals("Question not found", exception.getMessage());
        verify(questionService).updateQuestion(eq(999L), any(Question.class));
    }

    @Test
    void createQuestion_WithInvalidData_DelegatesValidationToService() {
        // Arrange
        when(questionService.saveQuestion(any(Question.class)))
                .thenThrow(new IllegalArgumentException("Invalid question content"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                questionController.createQuestion(questionDTO)
        );
        assertEquals("Invalid question content", exception.getMessage());
        verify(questionService).saveQuestion(any(Question.class));
    }
}