package com.app.Memora.progressTraking.entities;

import com.app.Memora.card.entities.Card;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Data
@Table(name = "progress_cards")
public class ProgressCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalAttempts;
    private int correctAttempts;
    private int score;

    // Add the nextReviewDate field
    @Column(name = "next_review_date")
    private LocalDateTime nextReviewDate;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    // Add last review date
    @Column(name = "last_review_date")
    private LocalDateTime lastReviewDate;

    @PrePersist
    protected void onCreate() {
        if (nextReviewDate == null) {
            nextReviewDate = LocalDateTime.now(ZoneOffset.UTC);
        }
        lastReviewDate = LocalDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    protected void onUpdate() {
        lastReviewDate = LocalDateTime.now(ZoneOffset.UTC);
    }
}
