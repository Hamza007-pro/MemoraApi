package com.app.Memora.card.services;

import com.app.Memora.card.dtos.CardReadDTO;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.content.services.ContentService;
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

    @Override
    public List<Card> getAllCards() {
        return null;
    }

    @Override
    public Card createCard(Card card, Long deckId) {
        return null;
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
        return null;
    }
}
