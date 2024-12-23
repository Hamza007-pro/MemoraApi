//package com.app.Memora.progress.controllers;
//
//import com.app.Memora.card.entities.Card;
//import com.app.Memora.progress.dtos.ProgressCardCreationDTO;
//import com.app.Memora.progress.dtos.ProgressCardReadDTO;
//import com.app.Memora.progress.dtos.ProgressCardUpdateDTO;
//import com.app.Memora.progress.entities.ProgressCard;
//import com.app.Memora.progress.services.ProgressService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProgressControllerTest {
//
//    @Mock
//    private ProgressService progressService;
//
//    @InjectMocks
//    private ProgressController progressController;
//
//    private ProgressCard progressCard;
//    private ProgressCardReadDTO progressCardReadDTO;
//    private ProgressCardCreationDTO progressCardCreationDTO;
//    private ProgressCardUpdateDTO progressCardUpdateDTO;
//    private Card card;
//    private Long deckId;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize test data
//        deckId = 1L;
//
//        card = new Card();
//        card.setId(1L);
//
//        progressCard = new ProgressCard();
//        progressCard.setId(1L);
//        progressCard.setCard(card);
//        progressCard.setLastReviewDate(LocalDateTime.now());
//        progressCard.setNextReviewDate(LocalDateTime.now().plusDays(1));
//        progressCard.setEaseFactor(2.5);
//        progressCard.setInterval(1);
//        progressCard.setReviewCount(0);
//
//        progressCardReadDTO = new ProgressCardReadDTO();
//        progressCardReadDTO.setId(1L);
//        progressCardReadDTO.setCardId(card.getId());
//        progressCardReadDTO.setLastReviewDate(progressCard.getLastReviewDate());
//        progressCardReadDTO.setNextReviewDate(progressCard.getNextReviewDate());
//        progressCardReadDTO.setEaseFactor(progressCard.getEaseFactor());
//        progressCardReadDTO.setInterval(progressCard.getInterval());
//        progressCardReadDTO.setReviewCount(progressCard.getReviewCount());
//
//        progressCardCreationDTO = new ProgressCardCreationDTO();
//        progressCardCreationDTO.setCardId(card.getId());
//
//        progressCardUpdateDTO = new ProgressCardUpdateDTO();
//        progressCardUpdateDTO.setEaseFactor(2.8);
//        progressCardUpdateDTO.setInterval(2);
//        progressCardUpdateDTO.setReviewCount(1);
//    }
//
//    @Test
//    void getAllProgressCards_ReturnsListOfProgressCards() {
//        // Arrange
//        List<ProgressCard> progressCards = Arrays.asList(progressCard);
//        when(progressService.getAllProgressCards()).thenReturn(progressCards);
//        when(progressService.convertToReadDTO(any(ProgressCard.class))).thenReturn(progressCardReadDTO);
//
//        // Act
//        List<ProgressCardReadDTO> result = progressController.getAllProgressCards();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(progressCardReadDTO, result.get(0));
//        verify(progressService).getAllProgressCards();
//    }
//
//    @Test
//    void getProgressCardById_WhenProgressCardExists_ReturnsProgressCard() {
//        // Arrange
//        when(progressService.getProgressCardById(1L)).thenReturn(Optional.of(progressCard));
//        when(progressService.convertToReadDTO(progressCard)).thenReturn(progressCardReadDTO);
//
//        // Act
//        ResponseEntity<ProgressCardReadDTO> response = progressController.getProgressCardById(1L);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(progressCardReadDTO, response.getBody());
//        verify(progressService).getProgressCardById(1L);
//    }
//
//    @Test
//    void getProgressCardById_WhenProgressCardDoesNotExist_ReturnsNotFound() {
//        // Arrange
//        when(progressService.getProgressCardById(1L)).thenReturn(Optional.empty());
//
//        // Act
//        ResponseEntity<ProgressCardReadDTO> response = progressController.getProgressCardById(1L);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(progressService).getProgressCardById(1L);
//    }
//
//    @Test
//    void createProgressCard_ReturnsCreatedProgressCard() {
//        // Arrange
//        when(progressService.createProgressCard(any(ProgressCard.class), eq(deckId)))
//                .thenReturn(progressCard);
//        when(progressService.convertToReadDTO(progressCard)).thenReturn(progressCardReadDTO);
//
//        // Act
//        ProgressCardReadDTO result = progressController.createProgressCard(deckId, progressCardCreationDTO);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(progressCardReadDTO.getId(), result.getId());
//        assertEquals(progressCardReadDTO.getCardId(), result.getCardId());
//        assertEquals(progressCardReadDTO.getEaseFactor(), result.getEaseFactor());
//        verify(progressService).createProgressCard(any(ProgressCard.class), eq(deckId));
//    }
//
//    @Test
//    void updateProgressCard_WhenProgressCardExists_ReturnsUpdatedProgressCard() {
//        // Arrange
//        when(progressService.getProgressCardById(1L)).thenReturn(Optional.of(progressCard));
//        when(progressService.updateProgressCard(eq(1L), any(ProgressCard.class))).thenReturn(progressCard);
//        when(progressService.convertToReadDTO(progressCard)).thenReturn(progressCardReadDTO);
//
//        // Act
//        ResponseEntity<ProgressCardReadDTO> response = progressController.updateProgressCard(1L, progressCardUpdateDTO);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(progressCardReadDTO, response.getBody());
//        verify(progressService).getProgressCardById(1L);
//        verify(progressService).updateProgressCard(eq(1L), any(ProgressCard.class));
//    }
//
//    @Test
//    void updateProgressCard_WhenProgressCardDoesNotExist_ReturnsNotFound() {
//        // Arrange
//        when(progressService.getProgressCardById(1L)).thenReturn(Optional.empty());
//
//        // Act
//        ResponseEntity<ProgressCardReadDTO> response = progressController.updateProgressCard(1L, progressCardUpdateDTO);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(progressService).getProgressCardById(1L);
//        verify(progressService, never()).updateProgressCard(any(Long.class), any(ProgressCard.class));
//    }
//
//    @Test
//    void deleteProgressCard_WhenProgressCardExists_ReturnsNoContent() {
//        // Arrange
//        when(progressService.getProgressCardById(1L)).thenReturn(Optional.of(progressCard));
//        doNothing().when(progressService).deleteProgressCard(1L);
//
//        // Act
//        ResponseEntity<Void> response = progressController.deleteProgressCard(1L);
//
//        // Assert
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(progressService).getProgressCardById(1L);
//        verify(progressService).deleteProgressCard(1L);
//    }
//
//    @Test
//    void deleteProgressCard_WhenProgressCardDoesNotExist_ReturnsNotFound() {
//        // Arrange
//        when(progressService.getProgressCardById(1L)).thenReturn(Optional.empty());
//
//        // Act
//        ResponseEntity<Void> response = progressController.deleteProgressCard(1L);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(progressService).getProgressCardById(1L);
//        verify(progressService, never()).deleteProgressCard(1L);
//    }
//}