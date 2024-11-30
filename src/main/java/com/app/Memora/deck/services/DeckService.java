package com.app.Memora.deck.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.deck.entities.Deck;

import java.util.List;

public interface DeckService {
    Deck createDeck(Deck deck);
    Deck updateDeck(Deck deck);
    void deleteDeck(Long id);
    List<Deck> getAllPublicDecks();
    List<Deck> getUserDecks();
    Deck getDeckById(Long id);
    List<Deck> searchDecks(String query);
    void addCardToDeck(Long deckId, Card card);
}
