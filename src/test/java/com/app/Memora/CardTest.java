package com.app.Memora;


import com.app.Memora.card.dtos.CardReadDTO;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.card.services.CardServiceImpl;
import com.app.Memora.content.services.ContentService;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.services.DeckService;
import com.app.Memora.enums.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private DeckService deckService;

    @Mock
    private ContentService contentService;

    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card());

        when(cardRepository.findAll()).thenReturn(cards);

        List<Card> allCards = cardService.getAllCards();

        assertEquals(1, allCards.size());
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    void testCreateCard() {
        Long deckId = 1L;
        Card card = new Card();
        Deck deck = new Deck();

        when(deckService.getDeckById(deckId)).thenReturn(Optional.of(deck));
        when(cardRepository.save(card)).thenReturn(card);

        Card createdCard = cardService.createCard(card, deckId);

        assertNotNull(createdCard);
        assertEquals(deck, createdCard.getDeck());
        verify(deckService, times(1)).getDeckById(deckId);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testCreateCardDeckNotFound() {
        Long deckId = 1L;
        Card card = new Card();

        when(deckService.getDeckById(deckId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cardService.createCard(card, deckId);
        });

        assertEquals("Deck not found", exception.getMessage());
        verify(deckService, times(1)).getDeckById(deckId);
        verify(cardRepository, never()).save(any(Card.class));
    }
//
//    @Test
//    void testGetCardById() {
//        Long cardId = 1L;
//        Card card = new Card();
//
//        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
//
//        Optional<Card> foundCard = cardService.getCardById(cardId);
//
//        assertTrue(foundCard.isPresent());
//        verify(cardRepository, times(1)).findById(cardId);
//    }

//    @Test
//    void testGetCardByIdNotFound() {
//        Long cardId = 1L;
//
//        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());
//
//        Optional<Card> foundCard = cardService.getCardById(cardId);
//
//        assertFalse(foundCard.isPresent());
//        verify(cardRepository, times(1)).findById(cardId);
//    }

}
