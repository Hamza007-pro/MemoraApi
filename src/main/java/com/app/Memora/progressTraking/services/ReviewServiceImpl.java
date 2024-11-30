package com.app.Memora.progressTraking.services;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.enums.DifficultyLevel;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.progressTraking.entities.ProgressCard;
import com.app.Memora.progressTraking.entities.ReviewStatistics;
import com.app.Memora.progressTraking.repositories.ProgressCardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final CardRepository cardRepository;
    private final ProgressCardRepository progressCardRepository;
    private final UserService userService;

    // Helper method to get current UTC datetime
    private LocalDateTime getCurrentDateTime() {
        return LocalDateTime.of(2024, 11, 29, 18, 13, 46);
    }

    @Override
    public List<Card> getDueCards(Long userId) {
        LocalDateTime currentDate = getCurrentDateTime();
        log.info("Getting due cards for user: {} at {}", "Hamza007-pro", currentDate);

        List<Card> dueCards = cardRepository.findDueCards(userId, currentDate);
        log.info("Found {} cards due for review", dueCards.size());
        return dueCards;
    }

    @Override
    public void scheduleNextReview(Long cardId, int performance) {

    }

    @Override
    public Map<LocalDate, Integer> getReviewSchedule(Long userId) {
        return null;
    }

    @Override
    public ReviewStatistics getReviewStatistics(Long userId) {
        return null;
    }

    @Override
    @Transactional
    public void recordReview(Long cardId, boolean wasCorrect) {
        log.info("Recording review for card {} by user {}", cardId, "Hamza007-pro");

        ProgressCard progress = progressCardRepository.findByCardId(cardId)
                .stream()
                .findFirst()
                .orElseGet(() -> createNewProgressCard(cardId));

        updateProgress(progress, wasCorrect);
        progressCardRepository.save(progress);
    }

    private ProgressCard createNewProgressCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found: " + cardId));

        ProgressCard progress = new ProgressCard();
        progress.setCard(card);
        progress.setTotalAttempts(0);
        progress.setCorrectAttempts(0);
        progress.setScore(0);
        progress.setNextReviewDate(getCurrentDateTime());
        progress.setLastReviewDate(getCurrentDateTime());
        return progress;
    }

    private void updateProgress(ProgressCard progress, boolean wasCorrect) {
        progress.setTotalAttempts(progress.getTotalAttempts() + 1);
        if (wasCorrect) {
            progress.setCorrectAttempts(progress.getCorrectAttempts() + 1);
        }

        int newScore = calculateScore(progress);
        progress.setScore(newScore);

        LocalDateTime nextReview = calculateNextReviewDate(newScore);
        progress.setNextReviewDate(nextReview);
        progress.setLastReviewDate(getCurrentDateTime());

        log.info("Updated progress for card {}. New score: {}, Next review: {}",
                progress.getCard().getId(), newScore, nextReview);
    }

    private int calculateScore(ProgressCard progress) {
        return (int) ((double) progress.getCorrectAttempts() / progress.getTotalAttempts() * 100);
    }

    private LocalDateTime calculateNextReviewDate(int score) {
        LocalDateTime currentDate = getCurrentDateTime();

        // Spaced repetition intervals
        if (score < 50) {
            return currentDate.plusHours(4);  // Review in 4 hours
        } else if (score < 70) {
            return currentDate.plusDays(1);   // Review tomorrow
        } else if (score < 90) {
            return currentDate.plusDays(3);   // Review in 3 days
        } else {
            return currentDate.plusDays(7);   // Review in a week
        }
    }

    @Override
    public ReviewStatistics getStatistics(Long userId) {
        List<ProgressCard> userProgress = progressCardRepository.findByUserId(userId);

        ReviewStatistics stats = new ReviewStatistics();
        stats.setTotalReviews(0);
        stats.setCorrectReviews(0);
        Map<DifficultyLevel, Integer> difficultyCount = new HashMap<>();

        for (ProgressCard progress : userProgress) {
            stats.setTotalReviews(stats.getTotalReviews() + progress.getTotalAttempts());
            stats.setCorrectReviews(stats.getCorrectReviews() + progress.getCorrectAttempts());

            DifficultyLevel difficulty = progress.getCard().getDifficultyLevel();
            difficultyCount.merge(difficulty, 1, Integer::sum);

            // Update last review date if this is the most recent
            if (stats.getLastReviewDate() == null ||
                    progress.getLastReviewDate().isAfter(stats.getLastReviewDate())) {
                stats.setLastReviewDate(progress.getLastReviewDate());
            }
        }

        if (stats.getTotalReviews() > 0) {
            double averageScore = (double) stats.getCorrectReviews() / stats.getTotalReviews() * 100;
            stats.setAverageScore(averageScore);
        }

        stats.setReviewsByDifficulty(difficultyCount);

        return stats;
    }
}