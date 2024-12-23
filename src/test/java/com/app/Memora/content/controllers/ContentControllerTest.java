package com.app.Memora.content.controllers;

import com.app.Memora.answer.dtos.AnswerDTO;
import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.services.AnswerService;
import com.app.Memora.content.dtos.ContentCreationDTO;
import com.app.Memora.content.dtos.ContentEditDTO;
import com.app.Memora.content.dtos.ContentReadDTO;
import com.app.Memora.content.entities.Content;
import com.app.Memora.content.services.ContentService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentControllerTest {

    @Mock
    private ContentService contentService;

    @Mock
    private QuestionService questionService;

    @Mock
    private AnswerService answerService;

    @InjectMocks
    private ContentController contentController;

    private Content content;
    private ContentReadDTO contentReadDTO;
    private ContentCreationDTO contentCreationDTO;
    private ContentEditDTO contentEditDTO;
    private Question question;
    private Answer answer;
    private QuestionDTO questionDTO;
    private AnswerDTO answerDTO;

    @BeforeEach
    void setUp() {
        // Initialize test data
        LocalDateTime now = LocalDateTime.now();

        question = new Question();
        question.setId(1L);
        question.setContent("Test Question");

        questionDTO = new QuestionDTO();
        questionDTO.setId(1L);
        questionDTO.setContent("Test Question");

        answer = new Answer();
        answer.setId(1L);
        answer.setContent("Test Answer");

        answerDTO = new AnswerDTO();
        answerDTO.setId(1L);
        answerDTO.setContent("Test Answer");

        content = new Content();
        content.setId(1L);
        content.setImage("test-image.jpg");
        content.setQuestion(question);
        content.setAnswer(answer);

        contentReadDTO = new ContentReadDTO();
        contentReadDTO.setId(1L);
        contentReadDTO.setImage("test-image.jpg");
        contentReadDTO.setDateCreated(now);
        contentReadDTO.setDateModified(now);
        contentReadDTO.setQuestion(questionDTO);
        contentReadDTO.setAnswer(answerDTO);

        contentCreationDTO = new ContentCreationDTO();
        contentCreationDTO.setImage("test-image.jpg");
        contentCreationDTO.setQuestionId(1L);
        contentCreationDTO.setAnswerId(1L);

        contentEditDTO = new ContentEditDTO();
        contentEditDTO.setImage("updated-image.jpg");
        contentEditDTO.setQuestionId(1L);
        contentEditDTO.setAnswerId(1L);
    }

    @Test
    void createContent_ReturnsCreatedContent() {
        // Arrange
        when(questionService.getQuestionById(1L)).thenReturn(Optional.of(question));
        when(answerService.getAnswerById(1L)).thenReturn(Optional.of(answer));
        when(contentService.saveContent(any(Content.class))).thenReturn(content);
        when(contentService.convertToReadDTO(any(Content.class))).thenReturn(contentReadDTO);

        // Act
        ContentReadDTO result = contentController.createContent(contentCreationDTO);

        // Assert
        assertNotNull(result);
        assertEquals(contentReadDTO.getId(), result.getId());
        assertEquals(contentReadDTO.getImage(), result.getImage());
        assertEquals(contentReadDTO.getQuestion().getId(), result.getQuestion().getId());
        assertEquals(contentReadDTO.getAnswer().getId(), result.getAnswer().getId());
        verify(contentService).saveContent(any(Content.class));
        verify(questionService).getQuestionById(1L);
        verify(answerService).getAnswerById(1L);
    }

    @Test
    void getContentById_WhenContentExists_ReturnsContent() {
        // Arrange
        when(contentService.getContentById(1L)).thenReturn(Optional.of(content));
        when(contentService.convertToReadDTO(content)).thenReturn(contentReadDTO);

        // Act
        ResponseEntity<ContentReadDTO> response = contentController.getContentById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(contentReadDTO, response.getBody());
        verify(contentService).getContentById(1L);
    }

    @Test
    void getContentById_WhenContentDoesNotExist_ReturnsNotFound() {
        // Arrange
        when(contentService.getContentById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ContentReadDTO> response = contentController.getContentById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(contentService).getContentById(1L);
    }

    @Test
    void getAllContents_ReturnsListOfContents() {
        // Arrange
        List<Content> contents = Arrays.asList(content);
        when(contentService.getAllContents()).thenReturn(contents);
        when(contentService.convertToReadDTO(any(Content.class))).thenReturn(contentReadDTO);

        // Act
        List<ContentReadDTO> result = contentController.getAllContents();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contentReadDTO, result.get(0));
        verify(contentService).getAllContents();
    }

    @Test
    void updateContent_WhenContentExists_ReturnsUpdatedContent() {
        // Arrange
        when(contentService.getContentById(1L)).thenReturn(Optional.of(content));
        when(questionService.getQuestionById(1L)).thenReturn(Optional.of(question));
        when(answerService.getAnswerById(1L)).thenReturn(Optional.of(answer));
        when(contentService.updateContent(eq(1L), any(Content.class))).thenReturn(content);
        when(contentService.convertToReadDTO(content)).thenReturn(contentReadDTO);

        // Act
        ResponseEntity<ContentReadDTO> response = contentController.updateContent(1L, contentEditDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(contentReadDTO, response.getBody());
        verify(contentService).updateContent(eq(1L), any(Content.class));
    }

    @Test
    void deleteContent_ReturnsNoContent() {
        // Arrange
        doNothing().when(contentService).deleteContent(1L);

        // Act
        ResponseEntity<Void> response = contentController.deleteContent(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contentService).deleteContent(1L);
    }
}