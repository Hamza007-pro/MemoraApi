package com.app.Memora.card.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.enums.DifficultyLevel;

import java.util.List;

public interface CardService {
    Card createCard(Card card, Long deckId);
    Card updateCard(Card card);
    void deleteCard(Long id);
    Card getCardById(Long id);
    List<Card> getCardsByDeck(Long deckId);
    List<Card> getCardsByDifficulty(DifficultyLevel difficulty);
}
