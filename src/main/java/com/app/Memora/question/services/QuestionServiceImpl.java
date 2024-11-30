package com.app.Memora.question.services;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.question.entities.Question;
import com.app.Memora.question.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Question createQuestion(Question question) {
        log.info("Creating new question by user: {}",   userService.getCurrentUser().getId());
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return null;
    }

    @Override
    public void deleteQuestion(Long id) {

    }

    @Override
    public Question getQuestionById(Long id) {
        return null;
    }

    @Override
    public List<Question> searchQuestions(String query) {
        return questionRepository.findByContentContainingIgnoreCase(query);
    }

    // Other methods implementation...
}