package com.app.Memora.enroll.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.enroll.entities.Enroll;
import com.app.Memora.enroll.services.EnrollService;
import com.app.Memora.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Slf4j
public class EnrollController {
    private final EnrollService enrollService;
    private final UserService userService;

    @PostMapping("/decks/{deckId}")
    public ResponseEntity<ApiResponse<Enroll>> enrollInDeck(@PathVariable Long deckId) {
        log.info("User {} enrolling in deck {}", userService.getCurrentUser(), deckId);
        Enroll enrollment = enrollService.enrollUserInDeck(userService.getCurrentUser().getId(), deckId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Enrolled successfully", enrollment));
    }

    @DeleteMapping("/decks/{deckId}")
    public ResponseEntity<ApiResponse<Void>> unenrollFromDeck(@PathVariable Long deckId) {
        enrollService.unenrollUserFromDeck(userService.getCurrentUser().getId(), deckId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Unenrolled successfully", null));
    }
}
