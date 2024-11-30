package com.app.Memora.progressTraking.repositories;

import com.app.Memora.progressTraking.entities.ProgressCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressCardRepository extends JpaRepository<ProgressCard, Long> {
    Optional<ProgressCard> findByCardId(Long cardId);

    @Query("SELECT pc FROM ProgressCard pc WHERE pc.card.deck.id = :deckId AND pc.correctAttempts = 0")
    List<ProgressCard> findUnsolvedCardsInDeck(Long deckId);

    @Query("SELECT pc FROM ProgressCard pc WHERE pc.card.deck.id = :deckId ORDER BY pc.score DESC")
    List<ProgressCard> findCardsByDeckOrderByScore(Long deckId);

    @Query("""
            SELECT pc FROM ProgressCard pc
            JOIN pc.card c
            JOIN c.deck d
            JOIN d.enrollments e
            WHERE e.user.id = :userId
            """)
    List<ProgressCard> findByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT pc FROM ProgressCard pc
            WHERE pc.nextReviewDate <= :currentDate
            AND pc.card.deck.id IN (
                SELECT d.id FROM Deck d
                JOIN d.enrollments e
                WHERE e.user.id = :userId
            )
            """)
    List<ProgressCard> findDueReviews(@Param("userId") Long userId,
                                      @Param("currentDate") LocalDateTime currentDate);
}
