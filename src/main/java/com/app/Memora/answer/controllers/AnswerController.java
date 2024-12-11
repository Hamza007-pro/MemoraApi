package com.app.Memora.answer.controllers;

import com.app.Memora.answer.dtos.AnswerDTO;
import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.services.AnswerService;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
@Slf4j
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @PostMapping
    public AnswerDTO createAnswer(@RequestBody AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setContent(answerDTO.getContent());
        Answer savedAnswer = answerService.saveAnswer(answer);
        answerDTO.setId(savedAnswer.getId());
        return answerDTO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable Long id) {
        return answerService.getAnswerById(id)
                .map(answer -> {
                    AnswerDTO answerDTO = new AnswerDTO();
                    answerDTO.setId(answer.getId());
                    answerDTO.setContent(answer.getContent());
                    return ResponseEntity.ok(answerDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<AnswerDTO> getAllAnswers() {
        return answerService.getAllAnswers().stream().map(answer -> {
            AnswerDTO answerDTO = new AnswerDTO();
            answerDTO.setId(answer.getId());
            answerDTO.setContent(answer.getContent());
            return answerDTO;
        }).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerDTO> updateAnswer(@PathVariable Long id, @RequestBody AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setContent(answerDTO.getContent());
        Answer updatedAnswer = answerService.updateAnswer(id, answer);
        answerDTO.setId(updatedAnswer.getId());
        return ResponseEntity.ok(answerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
