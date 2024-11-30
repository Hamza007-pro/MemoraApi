package com.app.Memora.progressTraking.repositories;

import com.app.Memora.progressTraking.entities.ProgressDeck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProgressDeckRepository extends JpaRepository<ProgressDeck, Long> {
    List<ProgressDeck> findByLastReviewDateBefore(LocalDateTime date);
    List<ProgressDeck> findByScoreGreaterThan(int score);
}
