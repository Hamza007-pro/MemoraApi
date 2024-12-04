package com.app.Memora.content.controllers;

import com.app.Memora.answer.entities.Answer;
import com.app.Memora.answer.services.AnswerService;
import com.app.Memora.authentication.services.UserService;
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
    public Content createContent(@RequestBody Content content, @PathParam("questionId") Long questionId , @PathParam("answerId") Long answerId) {
        Question question = questionService.getQuestionById(questionId).orElseThrow();
        Answer answer = answerService.getAnswerById(answerId).orElseThrow();
        content.setQuestion(question);
        content.setAnswer(answer);
        return contentService.saveContent(content);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getContentById(@PathVariable Long id ) {
        return contentService.getContentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Content> getAllContents() {
        return contentService.getAllContents();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Content> updateContent(@PathVariable Long id,
                                                 @RequestBody Content contentDetails,
                                                 @PathParam("questionId") Long questionId ,
                                                 @PathParam("answerId") Long answerId) {

        contentDetails.setQuestion(questionService.getQuestionById(questionId).orElseThrow());
        contentDetails.setAnswer(answerService.getAnswerById(answerId).orElseThrow());
        return ResponseEntity.ok(contentService.updateContent(id, contentDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }
}