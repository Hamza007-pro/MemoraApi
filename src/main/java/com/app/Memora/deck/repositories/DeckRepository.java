package com.app.Memora.deck.repositories;

import com.app.Memora.deck.entities.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findByIsPublicTrue();
    List<Deck> findByNameContainingIgnoreCase(String name);

    @Query("SELECT d FROM Deck d WHERE d.isPublic = true AND :categoryId IN (SELECT c.id FROM d.categories c)")
    List<Deck> findPublicDecksByCategory(Long categoryId);

    @Query("SELECT d FROM Deck d JOIN d.enrollments e WHERE e.user.id = :userId")
    List<Deck> findEnrolledDecksByUserId(Long userId);
}
