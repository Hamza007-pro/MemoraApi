package com.app.Memora.progressTraking.entities;

import com.app.Memora.enums.DifficultyLevel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ReviewStatistics {
    private int totalReviews;
    private int correctReviews;
    private double averageScore;
    private Map<DifficultyLevel, Integer> reviewsByDifficulty;
    private LocalDateTime lastReviewDate;
}
