package com.app.Memora.card.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public Card createCard(Card card, Long deckId) {
        return cardRepository.save(card);
    }

    public Card updateCard(Long id, Card cardDetails) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new RuntimeException("Card not found"));
        card.setDifficultyLevel(cardDetails.getDifficultyLevel());
        card.setContent(cardDetails.getContent());
        card.setDeck(cardDetails.getDeck());
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    // Other methods implementation...
}
