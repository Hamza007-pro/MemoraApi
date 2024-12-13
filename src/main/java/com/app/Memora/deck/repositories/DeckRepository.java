package com.app.Memora.deck.repositories;

import com.app.Memora.deck.entities.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    @Query("SELECT d FROM Deck d WHERE d.createdBy.id = :userId")
    List<Deck> findByUserId(@Param("userId") Long userId);

    @Query("SELECT d FROM Deck d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(d.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Deck> searchDecks(@Param("query") String query);
}
