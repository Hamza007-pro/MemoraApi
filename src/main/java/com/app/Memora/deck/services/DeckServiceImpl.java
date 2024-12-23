package com.app.Memora.deck.services;


import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.dtos.CardReadDTO;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.content.services.ContentService;
import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.repositories.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.app.Memora.categorie.entities.Category;
import com.app.Memora.enroll.entities.Enroll;


@Service
@Slf4j
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;
    private final ContentService contentService;
    private CardRepository cardRepository;


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
        // First check if the deck exists
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found with id: " + id));

        // Delete the deck
        deckRepository.delete(deck);
    }

    @Override
    public List<Deck> getAllDecks() {
        log.info("Fetching all decks");
        return deckRepository.findAll();
    }

    @Override
    public List<Deck> getUserDecks() {
        log.info("Fetch User Decks");
        return deckRepository.findByUserId(userService.getCurrentUser().getId());
    }

    @Override
    public Optional<Deck> getDeckById(Long id) {
        log.info("Get Deck by user");
        return deckRepository.findById(id);
    }

    @Override
    public List<Deck> searchDecks(String query) {
        log.info("Get deck that conatin keyword");
        return deckRepository.searchDecks(query);
    }

    @Override
    public DeckReadDTO convertToReadDTO(Deck deck) {
        var dto = new DeckReadDTO();
        dto.setId(deck.getId());
        dto.setName(deck.getName());
        dto.setDescription(deck.getDescription());
        dto.setImage(deck.getImage());
        dto.setPublic(deck.isPublic());
        dto.setCreatedAt(deck.getCreatedAt());
        dto.setUpdatedAt(deck.getUpdatedAt());
        dto.setCards(deck.getCards().stream().map(card -> {
            var cardDto = new CardReadDTO();
            cardDto.setId(card.getId());
            cardDto.setDifficultyLevel(card.getDifficultyLevel());
            cardDto.setContent(contentService.convertToReadDTO(card.getContent()));
            return cardDto;
        }).collect(Collectors.toList()));
        dto.setCategoryIds(deck.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        dto.setEnrollmentIds(deck.getEnrollments().stream().map(Enroll::getId).collect(Collectors.toList()));
        return dto;
    }
}
