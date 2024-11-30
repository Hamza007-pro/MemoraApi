package com.app.Memora.store.services;

import com.app.Memora.deck.entities.Deck;

import java.util.List;

public interface StoreService {
    void submitDeckForReview(Long deckId);
    void approveDeck(Long deckId);
    void rejectDeck(Long deckId);
    List<Deck> getPendingDecks();
    List<Deck> getApprovedDecks();
}
