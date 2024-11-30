package com.app.Memora.progressTraking.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.progressTraking.entities.ReviewStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudySessionService {
    private final ReviewService reviewService;

    public void startStudySession(Long userId) {
        // Get cards due for review
        List<Card> dueCards = reviewService.getDueCards(userId);

        // For each card user reviews
        for (Card card : dueCards) {
            // When user answers the card
            boolean wasCorrect = true; // This would come from user input
            reviewService.recordReview(card.getId(), wasCorrect);
        }

        // Get study statistics
        ReviewStatistics stats = reviewService.getStatistics(userId);
        System.out.println("Today's progress:");
        System.out.println("Cards reviewed: " + stats.getTotalReviews());
        System.out.println("Correct answers: " + stats.getCorrectReviews());
        System.out.println("Average score: " + stats.getAverageScore() + "%");
    }
}
