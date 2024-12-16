package com.app.Memora.card.services;

import com.app.Memora.card.dtos.CardReadDTO;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.content.services.ContentService;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.services.DeckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final DeckService deckService;

    @Autowired
    private ContentService contentService;

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Card createCard(Card card, Long deckId) {
        Deck deck = deckService.getDeckById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck not found"));
        card.setDeck(deck);
        return cardRepository.save(card);
    }

    @Override
    public Card updateCard(Long id, Card card) {
        return null;
    }

    @Override
    public void deleteCard(Long id) {

    }

    @Override
    public Optional<Card> getCardById(Long id) {
        return Optional.empty();
    }

    @Override
    public CardReadDTO convertToReadDTO(Card card) {
        var dto = new CardReadDTO();
        dto.setId(card.getId());
        dto.setContent(contentService.convertToReadDTO(card.getContent()));
        dto.setDifficultyLevel(card.getDifficultyLevel());
        return dto;
    }
}
