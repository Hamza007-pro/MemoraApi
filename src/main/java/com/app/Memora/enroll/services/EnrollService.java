package com.app.Memora.enroll.services;

import com.app.Memora.deck.entities.Deck;
import com.app.Memora.enroll.entities.Enroll;

import java.util.List;

public interface EnrollService {
    Enroll enrollUserInDeck(Long userId, Long deckId);
    void unenrollUserFromDeck(Long userId, Long deckId);
    List<Enroll> getUserEnrollments(Long userId);
    void updateProgress(Long enrollId, int progress);
    List<Deck> getRecommendedDecks(Long userId);
}
