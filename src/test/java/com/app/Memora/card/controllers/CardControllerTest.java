package com.app.Memora.card.controllers;

import com.app.Memora.card.dtos.CardCreationDTO;
import com.app.Memora.card.dtos.CardEditDTO;
import com.app.Memora.card.dtos.CardReadDTO;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.services.CardService;
import com.app.Memora.content.entities.Content;
import com.app.Memora.content.services.ContentService;
import com.app.Memora.enums.DifficultyLevel;
import com.app.Memora.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CardControllerTest {

    @Mock
    private CardService cardService;

    @Mock
    private ContentService contentService;

    @InjectMocks
    private CardController cardController;

    private MockMvc mockMvc;
    private Card testCard;
    private CardReadDTO testCardReadDTO;
    private Content testContent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();

        testContent = new Content();
        testContent.setId(1L);

        testCard = new Card();
        testCard.setId(1L);
        testCard.setDifficultyLevel(DifficultyLevel.EASY);
        testCard.setContent(testContent);

        testCardReadDTO = new CardReadDTO();
        testCardReadDTO.setId(1L);
        testCardReadDTO.setDifficultyLevel(DifficultyLevel.EASY);
    }

    @Test
    void updateCard_Success() throws Exception {
        CardEditDTO cardEditDTO = new CardEditDTO();
        cardEditDTO.setContentId(1L);
        cardEditDTO.setDifficultyLevel(DifficultyLevel.MEDIUM);

        when(contentService.getContentById(1L)).thenReturn(Optional.of(testContent));
        when(cardService.updateCard(eq(1L), any(Card.class))).thenReturn(testCard);
        when(cardService.convertToReadDTO(testCard)).thenReturn(testCardReadDTO);

        mockMvc.perform(put("/api/cards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contentId\":1,\"difficultyLevel\":\"MEDIUM\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.difficultyLevel").value("EASY"));

        verify(cardService, times(1)).updateCard(eq(1L), any(Card.class));
    }

    @Test
    void deleteCard_Success() throws Exception {
        doNothing().when(cardService).deleteCard(1L);

        mockMvc.perform(delete("/api/cards/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Card deleted successfully"));

        verify(cardService, times(1)).deleteCard(1L);
    }
}