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

    @Query("SELECT c FROM Card c JOIN c.deck d WHERE d.createdBy.id = :userId")
    List<Card> findDueCardsByUserId(@Param("userId") Long userId);
}
