package com.app.Memora.card.repositories;

import com.app.Memora.card.entities.Card;
import com.app.Memora.enums.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByDeckId(Long deckId);
    List<Card> findByDifficultyLevel(DifficultyLevel difficultyLevel);

    @Query("SELECT c FROM Card c WHERE c.deck.id = :deckId ORDER BY c.content.dateCreated DESC")
    List<Card> findByDeckIdOrderByDateCreated(Long deckId);

    @Query("""
            SELECT DISTINCT c FROM Card c
            JOIN c.deck d
            JOIN d.enrollments e
            JOIN e.progressDeck pd
            LEFT JOIN ProgressCard pc ON pc.card = c
            WHERE e.user.id = :userId
            AND (pc IS NULL OR pc.nextReviewDate <= :currentDate)
            ORDER BY pc.nextReviewDate ASC
            """)
    List<Card> findDueCards(@Param("userId") Long userId, @Param("currentDate") LocalDateTime currentDate);

}
