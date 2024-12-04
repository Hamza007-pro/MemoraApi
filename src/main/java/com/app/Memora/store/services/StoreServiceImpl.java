package com.app.Memora.store.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.enums.Status;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final DeckRepository deckRepository;

    @Override
    @Transactional
    public Deck submitDeckForReview(Long deckId, User user) {
        log.info("Submitting deck {} for review", deckId);
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));

        Store store = deck.getStore();
        deck.setStore(store);
        deck.setCreatedBy(user);
        deck.setPublic(false);
        return deckRepository.save(deck);
    }

    @Override
    public void approveDeck(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));
        deck.setPublic(true);
        deckRepository.save(deck);
    }

    @Override
    public void rejectDeck(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));
        deck.setPublic(false);
        deckRepository.save(deck);
    }

    @Override
    public List<Deck> getPendingDecks() {
        return null;
    }

    @Override
    public List<Deck> getApprovedDecks() {
        return null;
    }

    // Other methods implementation...
}
