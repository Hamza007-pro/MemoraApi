package com.app.Memora.progressTraking.entities;

import com.app.Memora.enroll.entities.Enroll;
import com.app.Memora.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "progress_decks")
public class ProgressDeck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    private LocalDateTime lastReviewDate;

    @OneToOne(mappedBy = "progressDeck")
    private Enroll enroll;
}
