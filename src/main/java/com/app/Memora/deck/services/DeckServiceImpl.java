package com.app.Memora.deck.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;
    private final UserService userService;
    private final CardRepository cardRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public Deck createDeck(Deck deck) {
        log.info("Creating new deck: {}", deck.getName());
        Long id = 1L;
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        User user = userService.getCurrentUser();
        deck.setCreatedBy(user);
        deck.setStore(store);

        // Ensure related entities are initialized
        Hibernate.initialize(deck.getStore());
        Hibernate.initialize(deck.getCreatedBy());

        return deckRepository.save(deck);
    }

    @Override
    public Deck updateDeck(Deck deck) {
        return null;
    }

    @Override
    public void deleteDeck(Long id) {

    }

    @Override
    public List<Deck> getAllPublicDecks() {
        return null;
    }

    @Override
    public List<Deck> getUserDecks() {
        User currentUser = userService.getCurrentUser();
        System.out.println("Current user: " + currentUser.getId());
        return deckRepository.findUserDecks(currentUser.getId());
    }

    @Override
    public Deck getDeckById(Long id) {
        return null;
    }

    @Override
    public List<Deck> searchDecks(String query) {
        return null;
    }


    @Override
    @Transactional
    public void addCardToDeck(Long deckId, Card card) {
        Deck deck = getDeckById(deckId);
        card.setDeck(deck);
        deck.getCards().add(card);
        cardRepository.save(card);
    }

    // Other methods implementation...
}