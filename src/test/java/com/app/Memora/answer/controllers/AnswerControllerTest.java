package com.app.Memora.answer.controllers;

import com.app.Memora.answer.dtos.AnswerDTO;
import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.services.AnswerService;
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
class AnswerControllerTest {

    @Mock
    private AnswerService answerService;

    @InjectMocks
    private AnswerController answerController;

    private Answer answer;
    private AnswerDTO answerDTO;

    @BeforeEach
    void setUp() {
        answer = new Answer();
        answer.setId(1L);
        answer.setContent("Test Answer Content");

        answerDTO = new AnswerDTO();
        answerDTO.setId(1L);
        answerDTO.setContent("Test Answer Content");
    }

    @Test
    void createAnswer_ReturnsCreatedAnswer() {
        // Arrange
        when(answerService.saveAnswer(any(Answer.class))).thenReturn(answer);

        // Act
        AnswerDTO result = answerController.createAnswer(answerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(answer.getId(), result.getId());
        assertEquals(answer.getContent(), result.getContent());
        verify(answerService).saveAnswer(any(Answer.class));
    }

    @Test
    void getAnswerById_WhenAnswerExists_ReturnsAnswer() {
        // Arrange
        when(answerService.getAnswerById(1L)).thenReturn(Optional.of(answer));

        // Act
        ResponseEntity<AnswerDTO> response = answerController.getAnswerById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(answer.getId(), response.getBody().getId());
        assertEquals(answer.getContent(), response.getBody().getContent());
        verify(answerService).getAnswerById(1L);
    }

    @Test
    void getAnswerById_WhenAnswerDoesNotExist_ReturnsNotFound() {
        // Arrange
        when(answerService.getAnswerById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AnswerDTO> response = answerController.getAnswerById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(answerService).getAnswerById(1L);
    }

    @Test
    void getAllAnswers_ReturnsListOfAnswers() {
        // Arrange
        List<Answer> answers = Arrays.asList(answer);
        when(answerService.getAllAnswers()).thenReturn(answers);

        // Act
        List<AnswerDTO> result = answerController.getAllAnswers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(answer.getId(), result.get(0).getId());
        assertEquals(answer.getContent(), result.get(0).getContent());
        verify(answerService).getAllAnswers();
    }

    @Test
    void updateAnswer_ReturnsUpdatedAnswer() {
        // Arrange
        when(answerService.updateAnswer(eq(1L), any(Answer.class))).thenReturn(answer);

        // Act
        ResponseEntity<AnswerDTO> response = answerController.updateAnswer(1L, answerDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(answer.getId(), response.getBody().getId());
        assertEquals(answer.getContent(), response.getBody().getContent());
        verify(answerService).updateAnswer(eq(1L), any(Answer.class));
    }

    @Test
    void deleteAnswer_ReturnsNoContent() {
        // Arrange
        doNothing().when(answerService).deleteAnswer(1L);

        // Act
        ResponseEntity<Void> response = answerController.deleteAnswer(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(answerService).deleteAnswer(1L);
    }
}