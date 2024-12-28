package com.app.Memora.card.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.enums.DifficultyLevel;
import com.app.Memora.content.entities.Content;
import com.app.Memora.deck.entities.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card testCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Content content = new Content();
        content.setId(1L);

        Deck deck = new Deck();
        deck.setId(1L);

        testCard = new Card();
        testCard.setId(1L);
        testCard.setDifficultyLevel(DifficultyLevel.EASY);
        testCard.setContent(content);
        testCard.setDeck(deck);
    }

    @Test
    void updateCard_Success() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(testCard));
        when(cardRepository.save(testCard)).thenReturn(testCard);

        Card updatedCard = new Card();
        updatedCard.setDifficultyLevel(DifficultyLevel.MEDIUM);
        updatedCard.setContent(testCard.getContent());
        updatedCard.setDeck(testCard.getDeck());

        Card result = cardService.updateCard(1L, updatedCard);

        assertNotNull(result);
        assertEquals(DifficultyLevel.MEDIUM, result.getDifficultyLevel());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).save(testCard);
    }

    @Test
    void updateCard_NotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        Card updatedCard = new Card();
        updatedCard.setDifficultyLevel(DifficultyLevel.MEDIUM);
        updatedCard.setContent(testCard.getContent());
        updatedCard.setDeck(testCard.getDeck());

        assertThrows(ResourceNotFoundException.class, () -> {
            cardService.updateCard(1L, updatedCard);
        });

        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(0)).save(any(Card.class));
    }

    @Test
    void deleteCard_Success() {
        when(cardRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cardRepository).deleteById(1L);

        cardService.deleteCard(1L);

        verify(cardRepository, times(1)).existsById(1L);
        verify(cardRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCard_NotFound() {
        when(cardRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            cardService.deleteCard(1L);
        });

        verify(cardRepository, times(1)).existsById(1L);
        verify(cardRepository, times(0)).deleteById(anyLong());
    }
}