package com.app.Memora.content.controllers;

import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.services.AnswerService;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.content.dtos.ContentCreationDTO;
import com.app.Memora.content.dtos.ContentEditDTO;
import com.app.Memora.content.dtos.ContentReadDTO;
import com.app.Memora.content.entities.Content;
import com.app.Memora.content.services.ContentService;
import com.app.Memora.question.entities.Question;
import com.app.Memora.question.services.QuestionService;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
@Slf4j
public class ContentController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @PostMapping
    public ContentReadDTO createContent(@RequestBody ContentCreationDTO contentCreationDTO) {
        Content content = new Content();
        content.setImage(contentCreationDTO.getImage());
        content.setQuestion(questionService.getQuestionById(contentCreationDTO.getQuestionId()).orElseThrow());
        content.setAnswer(answerService.getAnswerById(contentCreationDTO.getAnswerId()).orElseThrow());
        Content savedContent = contentService.saveContent(content);
        return contentService.convertToReadDTO(savedContent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentReadDTO> getContentById(@PathVariable Long id) {
        return contentService.getContentById(id)
                .map(contentService::convertToReadDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ContentReadDTO> getAllContents() {
        return contentService.getAllContents().stream()
                .map(contentService::convertToReadDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentReadDTO> updateContent(@PathVariable Long id, @RequestBody ContentEditDTO contentEditDTO) {
        Content content = contentService.getContentById(id).orElseThrow();
        content.setImage(contentEditDTO.getImage());
        content.setQuestion(questionService.getQuestionById(contentEditDTO.getQuestionId()).orElseThrow());
        content.setAnswer(answerService.getAnswerById(contentEditDTO.getAnswerId()).orElseThrow());
        Content updatedContent = contentService.updateContent(id, content);
        return ResponseEntity.ok(contentService.convertToReadDTO(updatedContent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }
}