package com.app.Memora;





import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.dtos.CardReadDTO;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.content.dtos.ContentReadDTO;
import com.app.Memora.content.entities.Content;
import com.app.Memora.content.services.ContentService;
import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.deck.services.DeckServiceImpl;
import com.app.Memora.enums.DifficultyLevel;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.repositories.StoreRepository;
import com.app.Memora.categorie.entities.Category;
import com.app.Memora.enroll.entities.Enroll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeckTest {

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private UserService userService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ContentService contentService;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private DeckServiceImpl deckService;

    private User currentUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize and set the current user
        currentUser = new User();
        currentUser.setId(1L); // Set the necessary fields of the User
        when(userService.getCurrentUser()).thenReturn(currentUser);
    }

    @Test
    void testCreateDeck() {
        Long storeId = 1L;
        Deck deck = new Deck();
        deck.setName("New Deck");

        Store store = new Store();
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(deckRepository.save(deck)).thenReturn(deck);

        Deck createdDeck = deckService.createDeck(storeId, deck);

        assertNotNull(createdDeck);
        assertEquals("New Deck", createdDeck.getName());
        assertEquals(currentUser, createdDeck.getCreatedBy());
        verify(storeRepository, times(1)).findById(storeId);
        verify(deckRepository, times(1)).save(deck);
    }

    @Test
    void testCreateDeckStoreNotFound() {
        Long storeId = 1L;
        Deck deck = new Deck();

        when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            deckService.createDeck(storeId, deck);
        });

        verify(storeRepository, times(1)).findById(storeId);
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void testUpdateDeck() {
        Long deckId = 1L;
        Deck deckDetails = new Deck();
        deckDetails.setName("Updated Deck");

        Deck existingDeck = new Deck();
        when(deckRepository.findById(deckId)).thenReturn(Optional.of(existingDeck));
        when(deckRepository.save(existingDeck)).thenReturn(existingDeck);

        Deck updatedDeck = deckService.updateDeck(deckId, deckDetails);

        assertNotNull(updatedDeck);
        assertEquals("Updated Deck", updatedDeck.getName());
        verify(deckRepository, times(1)).findById(deckId);
        verify(deckRepository, times(1)).save(existingDeck);
    }

    @Test
    void testUpdateDeckNotFound() {
        Long deckId = 1L;
        Deck deckDetails = new Deck();

        when(deckRepository.findById(deckId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            deckService.updateDeck(deckId, deckDetails);
        });

        verify(deckRepository, times(1)).findById(deckId);
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void testGetAllDecks() {
        List<Deck> decks = new ArrayList<>();
        decks.add(new Deck());

        when(deckRepository.findAll()).thenReturn(decks);

        List<Deck> allDecks = deckService.getAllDecks();

        assertEquals(1, allDecks.size());
        verify(deckRepository, times(1)).findAll();
    }

    @Test
    void testGetUserDecks() {
        List<Deck> decks = new ArrayList<>();
        decks.add(new Deck());

        when(deckRepository.findByUserId(currentUser.getId())).thenReturn(decks);

        List<Deck> userDecks = deckService.getUserDecks();

        assertEquals(1, userDecks.size());
        verify(userService, times(1)).getCurrentUser();
        verify(deckRepository, times(1)).findByUserId(currentUser.getId());
    }

    @Test
    void testGetDeckById() {
        Long deckId = 1L;
        Deck deck = new Deck();

        when(deckRepository.findById(deckId)).thenReturn(Optional.of(deck));

        Optional<Deck> foundDeck = deckService.getDeckById(deckId);

        assertTrue(foundDeck.isPresent());
        verify(deckRepository, times(1)).findById(deckId);
    }

    @Test
    void testSearchDecks() {
        String query = "test";
        List<Deck> decks = new ArrayList<>();
        decks.add(new Deck());

        when(deckRepository.searchDecks(query)).thenReturn(decks);

        List<Deck> searchResults = deckService.searchDecks(query);

        assertEquals(1, searchResults.size());
        verify(deckRepository, times(1)).searchDecks(query);
    }

    @Test
    void testConvertToReadDTO() {
        Deck deck = new Deck();
        deck.setId(1L);
        deck.setName("Test Deck");

        Card card = new Card();
        card.setId(1L);
        card.setDifficultyLevel(DifficultyLevel.EASY);
        deck.setCards(List.of(card));

        Category category = new Category();
        category.setId(1L);
        deck.setCategories(List.of(category));

        Enroll enroll = new Enroll();
        enroll.setId(1L);
        deck.setEnrollments(List.of(enroll));

        when(contentService.convertToReadDTO(any(Content.class))).thenReturn(new ContentReadDTO());

        DeckReadDTO dto = deckService.convertToReadDTO(deck);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Test Deck", dto.getName());
        assertEquals(1, dto.getCards().size());
        assertEquals(1, dto.getCategoryIds().size());
        assertEquals(1, dto.getEnrollmentIds().size());
    }
}