package com.app.Memora.authentication.controllers;

import com.app.Memora.deck.controllers.DeckController;
import com.app.Memora.deck.dtos.DeckCreationDTO;
import com.app.Memora.deck.dtos.DeckEditDTO;
import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.services.DeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeckControllerTest {

    @Mock
    private DeckService deckService;

    @InjectMocks
    private DeckController deckController;

    private Deck deck;
    private DeckReadDTO deckReadDTO;
    private DeckCreationDTO deckCreationDTO;
    private DeckEditDTO deckEditDTO;
    private Long storeId;

    @BeforeEach
    void setUp() {
        // Initialize test data
        storeId = 1L;

        deck = new Deck();
        deck.setId(1L);
        deck.setName("Test Deck");
        deck.setDescription("Test Description");
        deck.setImage("test-image.jpg");
        deck.setPublic(true);

        deckReadDTO = new DeckReadDTO();
        deckReadDTO.setId(1L);
        deckReadDTO.setName("Test Deck");
        deckReadDTO.setDescription("Test Description");
        deckReadDTO.setImage("test-image.jpg");
        deckReadDTO.setPublic(true);

        deckCreationDTO = new DeckCreationDTO();
        deckCreationDTO.setName("Test Deck");
        deckCreationDTO.setDescription("Test Description");
        deckCreationDTO.setImage("test-image.jpg");
        deckCreationDTO.setPublic(true);

        deckEditDTO = new DeckEditDTO();
        deckEditDTO.setName("Updated Deck");
        deckEditDTO.setDescription("Updated Description");
        deckEditDTO.setImage("updated-image.jpg");
        deckEditDTO.setPublic(false);
    }

    @Test
    void getAllDecks_ReturnsListOfDecks() {
        // Arrange
        List<Deck> decks = Arrays.asList(deck);
        when(deckService.getAllDecks()).thenReturn(decks);
        when(deckService.convertToReadDTO(any(Deck.class))).thenReturn(deckReadDTO);

        // Act
        List<DeckReadDTO> result = deckController.getAllDecks();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deckReadDTO, result.get(0));
        verify(deckService).getAllDecks();
    }

    @Test
    void getDeckById_WhenDeckExists_ReturnsDeck() {
        // Arrange
        when(deckService.getDeckById(1L)).thenReturn(Optional.of(deck));
        when(deckService.convertToReadDTO(deck)).thenReturn(deckReadDTO);

        // Act
        ResponseEntity<DeckReadDTO> response = deckController.getDeckById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deckReadDTO, response.getBody());
        verify(deckService).getDeckById(1L);
    }

    @Test
    void getDeckById_WhenDeckDoesNotExist_ReturnsNotFound() {
        // Arrange
        when(deckService.getDeckById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<DeckReadDTO> response = deckController.getDeckById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(deckService).getDeckById(1L);
    }

    @Test
    void createDeck_ReturnsCreatedDeck() {
        // Arrange
        when(deckService.createDeck(eq(storeId), any(Deck.class))).thenReturn(deck);
        when(deckService.convertToReadDTO(deck)).thenReturn(deckReadDTO);

        // Act
        DeckReadDTO result = deckController.createDeck(storeId, deckCreationDTO);

        // Assert
        assertNotNull(result);
        assertEquals(deckReadDTO, result);
        verify(deckService).createDeck(eq(storeId), any(Deck.class));
    }

    @Test
    void updateDeck_WhenDeckExists_ReturnsUpdatedDeck() {
        // Arrange
        when(deckService.getDeckById(1L)).thenReturn(Optional.of(deck));
        when(deckService.updateDeck(eq(1L), any(Deck.class))).thenReturn(deck);
        when(deckService.convertToReadDTO(deck)).thenReturn(deckReadDTO);

        // Act
        ResponseEntity<DeckReadDTO> response = deckController.updateDeck(1L, deckEditDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deckReadDTO, response.getBody());
        verify(deckService).updateDeck(eq(1L), any(Deck.class));
    }

    @Test
    void deleteDeck_ReturnsNoContent() {
        // Arrange
        doNothing().when(deckService).deleteDeck(1L);

        // Act
        ResponseEntity<Void> response = deckController.deleteDeck(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deckService).deleteDeck(1L);
    }

    @Test
    void getUserDecks_ReturnsUserDecks() {
        // Arrange
        List<Deck> userDecks = Arrays.asList(deck);
        when(deckService.getUserDecks()).thenReturn(userDecks);
        when(deckService.convertToReadDTO(any(Deck.class))).thenReturn(deckReadDTO);

        // Act
        List<DeckReadDTO> result = deckController.getUserDecks();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deckReadDTO, result.get(0));
        verify(deckService).getUserDecks();
    }

    @Test
    void searchDecks_ReturnsMatchingDecks() {
        // Arrange
        String query = "test";
        List<Deck> searchResults = Arrays.asList(deck);
        when(deckService.searchDecks(query)).thenReturn(searchResults);
        when(deckService.convertToReadDTO(any(Deck.class))).thenReturn(deckReadDTO);

        // Act
        List<DeckReadDTO> result = deckController.searchDecks(query);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deckReadDTO, result.get(0));
        verify(deckService).searchDecks(query);
    }
}