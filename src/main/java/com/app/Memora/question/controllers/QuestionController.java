package com.app.Memora.question.controllers;

import com.app.Memora.question.dtos.QuestionDTO;
import com.app.Memora.question.entities.Question;
import com.app.Memora.question.services.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping
    public QuestionDTO createQuestion(@RequestBody QuestionDTO questionDTO) {
        Question question = new Question();
        question.setContent(questionDTO.getContent());
        Question savedQuestion = questionService.saveQuestion(question);
        questionDTO.setId(savedQuestion.getId());
        return questionDTO;
    }


    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id)
                .map(question -> {
                    QuestionDTO questionDTO = new QuestionDTO();
                    questionDTO.setId(question.getId());
                    questionDTO.setContent(question.getContent());
                    return ResponseEntity.ok(questionDTO);
                }).orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public List<QuestionDTO> getAllQuestions() {
            return questionService.getAllQuestions().stream().map(question -> {
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setId(question.getId());
                questionDTO.setContent(question.getContent());
                return questionDTO;
            }).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {
        Question question = new Question();
        question.setContent(questionDTO.getContent());
        Question updatedQuestion = questionService.updateQuestion(id, question);
        questionDTO.setId(updatedQuestion.getId());
        return ResponseEntity.ok(questionDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
