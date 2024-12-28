package com.app.Memora.card.controllers;

import com.app.Memora.card.dtos.CardCreationDTO;
import com.app.Memora.card.dtos.CardEditDTO;
import com.app.Memora.card.dtos.CardReadDTO;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.services.CardService;
import com.app.Memora.content.services.ContentService;
import com.app.Memora.enums.DifficultyLevel;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@Slf4j
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private ContentService contentService;

    @GetMapping
    public List<CardReadDTO> getAllCards() {
        return cardService.getAllCards().stream()
                .map(cardService::convertToReadDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardReadDTO> getCardById(@PathVariable Long id) {
        return cardService.getCardById(id)
                .map(cardService::convertToReadDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{deckId}")
    public CardReadDTO createCard(@PathVariable Long deckId, @Valid @RequestBody CardCreationDTO cardCreationDTO) {
        Card card = new Card();
        card.setContent(contentService.getContentById(cardCreationDTO.getContentId())
                .orElseThrow(() -> new RuntimeException("Content not found")));
        card.setDifficultyLevel(DifficultyLevel.valueOf(String.valueOf(cardCreationDTO.getDifficultyLevel())));
        Card savedCard = cardService.createCard(card, deckId);
        return cardService.convertToReadDTO(savedCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardReadDTO> updateCard(@PathVariable Long id, @Valid @RequestBody CardEditDTO cardEditDTO) {
        Card card = new Card();
        card.setId(id);
        card.setContent(contentService.getContentById(cardEditDTO.getContentId())
                .orElseThrow(() -> new RuntimeException("Content not found")));
        card.setDifficultyLevel(DifficultyLevel.valueOf(String.valueOf(cardEditDTO.getDifficultyLevel())));
        Card updatedCard = cardService.updateCard(id, card);
        return ResponseEntity.ok(cardService.convertToReadDTO(updatedCard));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Card deleted successfully", null));
    }
}