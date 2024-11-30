package com.app.Memora.question.services;

import com.app.Memora.question.entities.Question;

import java.util.List;

public interface QuestionService {
    Question createQuestion(Question question);
    Question updateQuestion(Question question);
    void deleteQuestion(Long id);
    Question getQuestionById(Long id);
    List<Question> searchQuestions(String query);
}
