package com.app.Memora.answer.services;

import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.repositories.AnswerRepository;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.question.entities.Question;
import com.app.Memora.question.services.QuestionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Optional<Answer> getAnswerById(Long id) {
        return answerRepository.findById(id);
    }

    @Override
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    @Override
    public Answer updateAnswer(Long id, Answer answerDetails) {
        Answer answer = answerRepository.findById(id).orElseThrow();
        answer.setContent(answerDetails.getContent());
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
    // Other methods implementation...
}

