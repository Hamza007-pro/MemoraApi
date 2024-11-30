package com.app.Memora.answer.services;

import com.app.Memora.answer.entities.Answer;

import java.util.List;

public interface AnswerService {
    Answer createAnswer(Answer answer);
    Answer updateAnswer(Answer answer);
    void deleteAnswer(Long id);
    Answer getAnswerById(Long id);
    List<Answer> searchAnswers(String query);
}
