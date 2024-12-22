package com.app.Memora;



import com.app.Memora.answer.dtos.AnswerDTO;
import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.repositories.AnswerRepository;
import com.app.Memora.answer.services.AnswerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnswerTest {

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerServiceImpl answerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAnswer_ShouldSaveAndReturnAnswer() {
        Answer answer = new Answer();
        answer.setContent("Sample Answer");

        when(answerRepository.save(answer)).thenReturn(answer);

        Answer savedAnswer = answerService.saveAnswer(answer);

        assertNotNull(savedAnswer);
        assertEquals("Sample Answer", savedAnswer.getContent());
        verify(answerRepository, times(1)).save(answer);
    }

    @Test
    void getAnswerById_ShouldReturnAnswer_WhenFound() {
        Answer answer = new Answer();
        answer.setId(1L);
        answer.setContent("Sample Answer");

        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));

        Optional<Answer> retrievedAnswer = answerService.getAnswerById(1L);

        assertTrue(retrievedAnswer.isPresent());
        assertEquals("Sample Answer", retrievedAnswer.get().getContent());
        verify(answerRepository, times(1)).findById(1L);
    }

    @Test
    void getAnswerById_ShouldReturnEmpty_WhenNotFound() {
        when(answerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Answer> retrievedAnswer = answerService.getAnswerById(1L);

        assertFalse(retrievedAnswer.isPresent());
        verify(answerRepository, times(1)).findById(1L);
    }

    @Test
    void getAllAnswers_ShouldReturnListOfAnswers() {
        Answer answer1 = new Answer();
        answer1.setId(1L);
        answer1.setContent("Answer 1");

        Answer answer2 = new Answer();
        answer2.setId(2L);
        answer2.setContent("Answer 2");

        when(answerRepository.findAll()).thenReturn(Arrays.asList(answer1, answer2));

        List<Answer> answers = answerService.getAllAnswers();

        assertEquals(2, answers.size());
        verify(answerRepository, times(1)).findAll();
    }

    @Test
    void updateAnswer_ShouldUpdateAndReturnAnswer() {
        Answer existingAnswer = new Answer();
        existingAnswer.setId(1L);
        existingAnswer.setContent("Old Content");

        Answer updatedDetails = new Answer();
        updatedDetails.setContent("New Content");

        when(answerRepository.findById(1L)).thenReturn(Optional.of(existingAnswer));
        when(answerRepository.save(existingAnswer)).thenReturn(existingAnswer);

        Answer updatedAnswer = answerService.updateAnswer(1L, updatedDetails);

        assertNotNull(updatedAnswer);
        assertEquals("New Content", updatedAnswer.getContent());
        verify(answerRepository, times(1)).findById(1L);
        verify(answerRepository, times(1)).save(existingAnswer);
    }

    @Test
    void deleteAnswer_ShouldDeleteAnswerById() {
        doNothing().when(answerRepository).deleteById(1L);

        answerService.deleteAnswer(1L);

        verify(answerRepository, times(1)).deleteById(1L);
    }

    @Test
    void convertToAnswerDTO_ShouldReturnCorrectDTO() {
        Answer answer = new Answer();
        answer.setId(1L);
        answer.setContent("Sample Content");

        AnswerDTO answerDTO = answerService.convertToAnswerDTO(answer);

        assertNotNull(answerDTO);
        assertEquals(1L, answerDTO.getId());
        assertEquals("Sample Content", answerDTO.getContent());
    }
}

