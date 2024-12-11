package com.app.Memora.deck.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.card.services.CardService;
import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.repositories.StoreRepository;
import com.app.Memora.store.services.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;


    @Override
    public Deck createDeck(Long storeId, Deck deck) {
        log.info("Creating new deck: {}", deck.getName());
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        deck.setStore(store);
        deck.setCreatedBy(userService.getCurrentUser());
        return deckRepository.save(deck);
    }

    @Override
    public Deck updateDeck(Long id, Deck deck) {
        Deck deck1 = deckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));
        deck1.setName(deck.getName());
        deck1.setDescription(deck.getDescription());
        deck1.setImage(deck.getImage());
        deck1.setPublic(deck.isPublic());
        return deckRepository.save(deck1);
    }

    @Override
    public void deleteDeck(Long id) {

    }

    @Override
    public List<Deck> getAllDecks() {
        log.info("Fetching all decks");
        return deckRepository.findAll();
    }

    @Override
    public List<Deck> getUserDecks(Long userId) {
        return null;
    }

    @Override
    public Optional<Deck> getDeckById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Deck> searchDecks(String query) {
        return null;
    }

    @Override
    public DeckReadDTO convertToReadDTO(Deck deck) {
        return null;
    }
}
