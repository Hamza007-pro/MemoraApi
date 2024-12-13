package com.app.Memora.deck.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.deck.entities.Deck;

import java.util.List;
import java.util.Optional;

public interface DeckService {
    Deck createDeck(Long storeId,Deck deck);
    Deck updateDeck(Long id, Deck deck);
    void deleteDeck(Long id);
    List<Deck> getAllDecks();
    List<Deck> getUserDecks();
    Optional<Deck> getDeckById(Long id);
    List<Deck> searchDecks(String query);
    DeckReadDTO convertToReadDTO(Deck deck);
}
