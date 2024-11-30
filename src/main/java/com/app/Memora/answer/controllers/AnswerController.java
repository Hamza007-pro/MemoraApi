package com.app.Memora.answer.controllers;

import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.services.AnswerService;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
@Slf4j
public class AnswerController {
    private final AnswerService answerService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Answer>> createAnswer(@Valid @RequestBody Answer answer) {
        log.info("Creating new answer by: {}", userService.getCurrentUser());
        Answer createdAnswer = answerService.createAnswer(answer);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Answer created successfully",
                createdAnswer,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Answer>> updateAnswer(@PathVariable Long id,
                                                            @Valid @RequestBody Answer answer) {
        answer.setId(id);
        Answer updatedAnswer = answerService.updateAnswer(answer);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Answer updated successfully",
                updatedAnswer,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Answer>>> searchAnswers(@RequestParam String query) {
        List<Answer> answers = answerService.searchAnswers(query);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Answers retrieved successfully",
                answers,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }
}
