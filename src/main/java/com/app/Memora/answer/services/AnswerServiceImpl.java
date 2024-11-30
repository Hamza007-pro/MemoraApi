package com.app.Memora.answer.services;

import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.repositories.AnswerRepository;
import com.app.Memora.authentication.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Answer createAnswer(Answer answer) {
        log.info("Creating new answer by user: {}", userService.getCurrentUser().getId());
        return answerRepository.save(answer);
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        return null;
    }

    @Override
    public void deleteAnswer(Long id) {

    }

    @Override
    public Answer getAnswerById(Long id) {
        return null;
    }

    @Override
    public List<Answer> searchAnswers(String query) {
        return answerRepository.findByContentContainingIgnoreCase(query);
    }

    // Other methods implementation...
}

