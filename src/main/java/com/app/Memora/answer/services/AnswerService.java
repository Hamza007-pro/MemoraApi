package com.app.Memora.answer.services;

import com.app.Memora.answer.dtos.AnswerDTO;
import com.app.Memora.answer.entities.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerService {
    Answer saveAnswer(Answer answer);
    Optional<Answer> getAnswerById(Long id);
    List<Answer> getAllAnswers();
    Answer updateAnswer(Long id, Answer answerDetails);
    void deleteAnswer(Long id);
    AnswerDTO convertToAnswerDTO(Answer answer);
}
