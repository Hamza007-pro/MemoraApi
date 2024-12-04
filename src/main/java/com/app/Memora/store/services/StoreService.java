package com.app.Memora.store.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.store.entities.Store;

import java.util.List;

public interface StoreService {
    Deck submitDeckForReview(Long deckId, User user);
    void approveDeck(Long deckId);
    void rejectDeck(Long deckId);
    List<Deck> getPendingDecks();
    List<Deck> getApprovedDecks();
}
