package com.app.Memora.question.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.question.entities.Question;
import com.app.Memora.question.services.QuestionService;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Question>> createQuestion(@Valid @RequestBody Question question) {
        log.info("Creating new question by: {}", userService.getCurrentUser());
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Question created successfully",
                createdQuestion,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> updateQuestion(@PathVariable Long id,
                                                                @Valid @RequestBody Question question) {
        question.setId(id);
        Question updatedQuestion = questionService.updateQuestion(question);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Question updated successfully",
                updatedQuestion,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Question>>> searchQuestions(@RequestParam String query) {
        List<Question> questions = questionService.searchQuestions(query);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Questions retrieved successfully",
                questions,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }
}
