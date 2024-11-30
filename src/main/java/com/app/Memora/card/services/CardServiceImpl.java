package com.app.Memora.card.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.enums.DifficultyLevel;
import com.app.Memora.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    @Override
    @Transactional
    public Card createCard(Card card, Long deckId) {
        log.info("Creating new card for deck: {}", deckId);
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));
        card.setDeck(deck);
        return cardRepository.save(card);
    }

    @Override
    public Card updateCard(Card card) {
        return null;
    }

    @Override
    public void deleteCard(Long id) {

    }

    @Override
    public Card getCardById(Long id) {
        return null;
    }

    @Override
    public List<Card> getCardsByDeck(Long deckId) {
        return null;
    }

    @Override
    public List<Card> getCardsByDifficulty(DifficultyLevel difficulty) {
        return null;
    }

    // Other methods implementation...
}
