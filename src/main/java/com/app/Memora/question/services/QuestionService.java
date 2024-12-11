package com.app.Memora.question.services;

import com.app.Memora.question.dtos.QuestionDTO;
import com.app.Memora.question.entities.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question saveQuestion(Question question);
    Optional<Question> getQuestionById(Long id);
    List<Question> getAllQuestions();
    Question updateQuestion(Long id, Question questionDetails);
    void deleteQuestion(Long id);
    QuestionDTO convertToQuestionDTO(Question question);
}
