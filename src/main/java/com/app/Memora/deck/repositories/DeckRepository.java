package com.app.Memora.deck.repositories;

import com.app.Memora.deck.entities.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    @Query("SELECT d FROM Deck d JOIN FETCH d.store JOIN FETCH d.createdBy WHERE d.createdBy.id = :userId")
    List<Deck> findUserDecks(@Param("userId") Long userId);
}
