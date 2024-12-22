package com.app.Memora;


import com.app.Memora.question.dtos.QuestionDTO;
import com.app.Memora.question.entities.Question;
import com.app.Memora.question.repositories.QuestionRepository;
import com.app.Memora.question.services.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveQuestion() {
        Question question = new Question();
        question.setContent("Test Content");

        when(questionRepository.save(question)).thenReturn(question);

        Question savedQuestion = questionService.saveQuestion(question);

        assertNotNull(savedQuestion);
        assertEquals("Test Content", savedQuestion.getContent());
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void testGetQuestionById() {
        Question question = new Question();
        question.setId(1L);
        question.setContent("Test Content");

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        Optional<Question> foundQuestion = questionService.getQuestionById(1L);

        assertTrue(foundQuestion.isPresent());
        assertEquals("Test Content", foundQuestion.get().getContent());
        verify(questionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllQuestions() {
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setContent("Content 1");
        Question question2 = new Question();
        question2.setContent("Content 2");
        questions.add(question1);
        questions.add(question2);

        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> allQuestions = questionService.getAllQuestions();

        assertEquals(2, allQuestions.size());
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    void testUpdateQuestion() {
        Question existingQuestion = new Question();
        existingQuestion.setId(1L);
        existingQuestion.setContent("Old Content");

        Question updatedDetails = new Question();
        updatedDetails.setContent("New Content");

        when(questionRepository.findById(1L)).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(existingQuestion)).thenReturn(existingQuestion);

        Question updatedQuestion = questionService.updateQuestion(1L, updatedDetails);

        assertNotNull(updatedQuestion);
        assertEquals("New Content", updatedQuestion.getContent());
        verify(questionRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).save(existingQuestion);
    }

    @Test
    void testDeleteQuestion() {
        Long questionId = 1L;

        doNothing().when(questionRepository).deleteById(questionId);

        questionService.deleteQuestion(questionId);

        verify(questionRepository, times(1)).deleteById(questionId);
    }

    @Test
    void testConvertToQuestionDTO() {
        Question question = new Question();
        question.setId(1L);
        question.setContent("Test Content");

        QuestionDTO questionDTO = questionService.convertToQuestionDTO(question);

        assertNotNull(questionDTO);
        assertEquals(1L, questionDTO.getId());
        assertEquals("Test Content", questionDTO.getContent());
    }
}
