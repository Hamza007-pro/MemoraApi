package com.app.Memora.content.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.content.entities.Content;
import com.app.Memora.content.services.ContentService;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ContentService contentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Content>> createContent(@Valid @RequestBody Content content) {
        log.info("Creating new content by: {}", userService.getCurrentUser());
        Content createdContent = contentService.createContent(content);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Content created successfully",
                createdContent,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Content>> updateContent(@PathVariable Long id,
                                                              @Valid @RequestBody Content content) {
        content.setId(id);
        Content updatedContent = contentService.updateContent(content);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Content updated successfully",
                updatedContent,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<Content>>> getRecentContent(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {
        List<Content> contents = contentService.getContentsByDateRange(since,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6));
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Recent content retrieved",
                contents,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }
}