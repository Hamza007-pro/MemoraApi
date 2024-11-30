package com.app.Memora.progressTraking.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.progressTraking.entities.ReviewStatistics;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReviewService {
    List<Card> getDueCards(Long userId);
    void scheduleNextReview(Long cardId, int performance);
    Map<LocalDate, Integer> getReviewSchedule(Long userId);
    ReviewStatistics getReviewStatistics(Long userId);

    @Transactional
    void recordReview(Long cardId, boolean wasCorrect);

    ReviewStatistics getStatistics(Long userId);
}