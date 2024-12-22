package com.app.Memora;


import com.app.Memora.answer.dtos.AnswerDTO;
import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.services.AnswerService;
import com.app.Memora.content.dtos.ContentReadDTO;
import com.app.Memora.content.entities.Content;
import com.app.Memora.content.repositories.ContentRepository;
import com.app.Memora.content.services.ContentServiceImpl;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.question.dtos.QuestionDTO;
import com.app.Memora.question.entities.Question;
import com.app.Memora.question.services.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContentTest {

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private QuestionService questionService;

    @Mock
    private AnswerService answerService;

    @InjectMocks
    private ContentServiceImpl contentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveContent() {
        Content content = new Content();
        content.setImage("image.png");

        when(contentRepository.save(content)).thenReturn(content);

        Content savedContent = contentService.saveContent(content);

        assertNotNull(savedContent);
        assertEquals("image.png", savedContent.getImage());
        verify(contentRepository, times(1)).save(content);
    }

    @Test
    void testGetContentById() {
        Content content = new Content();
        content.setId(1L);
        content.setImage("image.png");

        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));

        Optional<Content> foundContent = contentService.getContentById(1L);

        assertTrue(foundContent.isPresent());
        assertEquals("image.png", foundContent.get().getImage());
        verify(contentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllContents() {
        List<Content> contents = new ArrayList<>();
        Content content1 = new Content();
        content1.setImage("image1.png");
        Content content2 = new Content();
        content2.setImage("image2.png");
        contents.add(content1);
        contents.add(content2);

        when(contentRepository.findAll()).thenReturn(contents);

        List<Content> allContents = contentService.getAllContents();

        assertEquals(2, allContents.size());
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void testUpdateContent() {
        Long contentId = 1L;
        Content updatedDetails = new Content();
        updatedDetails.setImage("updated_image.png");

        Content existingContent = new Content();
        existingContent.setId(contentId);
        existingContent.setImage("old_image.png");

        when(contentRepository.findById(contentId)).thenReturn(Optional.of(existingContent));
        when(contentRepository.save(existingContent)).thenReturn(existingContent);

        Content updatedContent = contentService.updateContent(contentId, updatedDetails);

        assertNotNull(updatedContent);
        assertEquals("updated_image.png", updatedContent.getImage());
        verify(contentRepository, times(1)).findById(contentId);
        verify(contentRepository, times(1)).save(existingContent);
    }

    @Test
    void testDeleteContent() {
        Long contentId = 1L;

        doNothing().when(contentRepository).deleteById(contentId);

        contentService.deleteContent(contentId);

        verify(contentRepository, times(1)).deleteById(contentId);
    }

    @Test
    void testConvertToReadDTO() {
        Content content = new Content();
        content.setId(1L);
        content.setImage("image.png");
        content.setDateCreated(LocalDateTime.now());
        content.setDateModified(LocalDateTime.now());

        Question question = new Question();
        question.setId(1L);
        content.setQuestion(question);

        Answer answer = new Answer();
        answer.setId(1L);
        content.setAnswer(answer);

        QuestionDTO questionDTO = new QuestionDTO();
        when(questionService.convertToQuestionDTO(any(Question.class))).thenReturn(questionDTO);

        AnswerDTO answerDTO = new AnswerDTO();
        when(answerService.convertToAnswerDTO(any(Answer.class))).thenReturn(answerDTO);

        ContentReadDTO dto = contentService.convertToReadDTO(content);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("image.png", dto.getImage());
        assertEquals(questionDTO, dto.getQuestion());
        assertEquals(answerDTO, dto.getAnswer());
        verify(questionService, times(1)).convertToQuestionDTO(any(Question.class));
        verify(answerService, times(1)).convertToAnswerDTO(any(Answer.class));
    }
}
