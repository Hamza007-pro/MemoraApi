package com.app.Memora.card.controllers;

import com.app.Memora.card.dtos.CardCreationDTO;
import com.app.Memora.card.dtos.CardReadDTO;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.services.CardService;
import com.app.Memora.content.dtos.ContentReadDTO;
import com.app.Memora.content.entities.Content;
import com.app.Memora.content.services.ContentService;
import com.app.Memora.enums.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @Mock
    private CardService cardService;

    @Mock
    private ContentService contentService;

    @InjectMocks
    private CardController cardController;

    private Card card;
    private CardReadDTO cardReadDTO;
    private CardCreationDTO cardCreationDTO;
    private Content content;
    private ContentReadDTO contentReadDTO;
    private Long deckId;

    @BeforeEach
    void setUp() {
        // Initialize test data
        deckId = 1L;

        content = new Content();
        content.setId(1L);
        content.setImage("test-image.jpg");

        contentReadDTO = new ContentReadDTO();
        contentReadDTO.setId(1L);
        contentReadDTO.setImage("test-image.jpg");
        contentReadDTO.setDateCreated(LocalDateTime.now());
        contentReadDTO.setDateModified(LocalDateTime.now());

        card = new Card();
        card.setId(1L);
        card.setContent(content);
        card.setDifficultyLevel(DifficultyLevel.EASY);

        cardReadDTO = new CardReadDTO();
        cardReadDTO.setId(1L);
        cardReadDTO.setContent(contentReadDTO);
        cardReadDTO.setDifficultyLevel(DifficultyLevel.EASY);
        cardReadDTO.setProgressCardIds(new ArrayList<>());

        cardCreationDTO = new CardCreationDTO();
        cardCreationDTO.setContentId(1L);
        cardCreationDTO.setDifficultyLevel(DifficultyLevel.EASY);
    }

    @Test
    void getAllCards_ReturnsListOfCards() {
        // Arrange
        List<Card> cards = Arrays.asList(card);
        when(cardService.getAllCards()).thenReturn(cards);
        when(cardService.convertToReadDTO(any(Card.class))).thenReturn(cardReadDTO);

        // Act
        List<CardReadDTO> result = cardController.getAllCards();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cardReadDTO, result.get(0));
        verify(cardService).getAllCards();
        verify(cardService).convertToReadDTO(card);
    }

    @Test
    void getCardById_WhenCardExists_ReturnsCard() {
        // Arrange
        when(cardService.getCardById(1L)).thenReturn(Optional.of(card));
        when(cardService.convertToReadDTO(card)).thenReturn(cardReadDTO);

        // Act
        ResponseEntity<CardReadDTO> response = cardController.getCardById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cardReadDTO, response.getBody());
        verify(cardService).getCardById(1L);
        verify(cardService).convertToReadDTO(card);
    }

    @Test
    void getCardById_WhenCardDoesNotExist_ReturnsNotFound() {
        // Arrange
        when(cardService.getCardById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<CardReadDTO> response = cardController.getCardById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(cardService).getCardById(1L);
    }

    @Test
    void createCard_ReturnsCreatedCard() {
        // Arrange
        when(contentService.getContentById(1L))
                .thenReturn(Optional.of(content));
        when(cardService.createCard(any(Card.class), eq(deckId))).thenReturn(card);
        when(cardService.convertToReadDTO(card)).thenReturn(cardReadDTO);

        // Act
        CardReadDTO result = cardController.createCard(deckId, cardCreationDTO);

        // Assert
        assertNotNull(result);
        assertEquals(cardReadDTO.getId(), result.getId());
        assertEquals(cardReadDTO.getContent().getId(), result.getContent().getId());
        assertEquals(cardReadDTO.getDifficultyLevel(), result.getDifficultyLevel());
        verify(contentService).getContentById(1L);
        verify(cardService).createCard(any(Card.class), eq(deckId));
        verify(cardService).convertToReadDTO(card);
    }

    @Test
    void createCard_WhenContentNotFound_ThrowsRuntimeException() {
        // Arrange
        when(contentService.getContentById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                cardController.createCard(deckId, cardCreationDTO)
        );
        verify(contentService).getContentById(1L);
        verify(cardService, never()).createCard(any(Card.class), any(Long.class));
    }
}