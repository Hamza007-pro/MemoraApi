package com.app.Memora.card.services;

import com.app.Memora.card.entities.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    List<Card>getAllCards();
    Card createCard(Card card, Long deckId);
    Card updateCard(Long id, Card card);
    void deleteCard(Long id);
    Optional<Card> getCardById(Long id);
}
