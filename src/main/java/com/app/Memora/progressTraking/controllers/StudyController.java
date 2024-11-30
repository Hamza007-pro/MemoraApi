package com.app.Memora.progressTraking.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.entities.Card;
import com.app.Memora.progressTraking.entities.ReviewStatistics;
import com.app.Memora.progressTraking.services.ReviewService;
import com.app.Memora.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
@Slf4j
public class StudyController {
    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/due-cards")
    public ResponseEntity<ApiResponse<List<Card>>> getDueCards() {
        log.info("Fetching due cards for: {}", userService.getCurrentUser());
        List<Card> dueCards = reviewService.getDueCards(userService.getCurrentUser().getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Due cards retrieved", dueCards));
    }

    @PostMapping("/record-review")
    public ResponseEntity<ApiResponse<Void>> recordReview(@RequestParam Long cardId,
                                                          @RequestParam boolean correct) {
        reviewService.recordReview(cardId, correct);
        return ResponseEntity.ok(new ApiResponse<>(true, "Review recorded successfully", null));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<ReviewStatistics>> getStatistics() {
        ReviewStatistics stats = reviewService.getStatistics(userService.getCurrentUser().getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Statistics retrieved", stats));
    }
}
