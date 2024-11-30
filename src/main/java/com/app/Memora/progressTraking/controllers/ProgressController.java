package com.app.Memora.progressTraking.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.entities.Card;
import com.app.Memora.progressTraking.entities.ProgressCard;
import com.app.Memora.progressTraking.services.ProgressTrackingService;
import com.app.Memora.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
@Slf4j
public class ProgressController {
    private final ProgressTrackingService progressService;
    private final UserService userService;

    @GetMapping("/decks/{deckId}")
    public ResponseEntity<ApiResponse<List<ProgressCard>>> getDeckProgress(@PathVariable Long deckId) {
        List<ProgressCard> progress = progressService.getCardProgress(deckId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Progress retrieved", progress));
    }

    @GetMapping("/due-review")
    public ResponseEntity<ApiResponse<List<Card>>> getReviewDueCards() {
        List<Card> dueCards = progressService.getReviewDueCards(userService.getCurrentUser().getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Due reviews retrieved", dueCards));
    }
}
