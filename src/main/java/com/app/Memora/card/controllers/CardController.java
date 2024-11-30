package com.app.Memora.card.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.services.CardService;
import com.app.Memora.enums.DifficultyLevel;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@Slf4j
public class CardController {
    private final CardService cardService;
    private final UserService userService;

    @PostMapping("/decks/{deckId}")
    public ResponseEntity<ApiResponse<Card>> createCard(@PathVariable Long deckId,
                                                        @Valid @RequestBody Card card) {
        log.info("Creating new card in deck {} by: {}", deckId, userService.getCurrentUser());
        Card createdCard = cardService.createCard(card, deckId);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Card created successfully",
                createdCard,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Card>> updateCard(@PathVariable Long id,
                                                        @Valid @RequestBody Card card) {
        card.setId(id);
        Card updatedCard = cardService.updateCard(card);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Card updated successfully",
                updatedCard,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Card deleted successfully",
                null,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @GetMapping("/deck/{deckId}")
    public ResponseEntity<ApiResponse<List<Card>>> getCardsByDeck(@PathVariable Long deckId) {
        List<Card> cards = cardService.getCardsByDeck(deckId);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Cards retrieved successfully",
                cards,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @GetMapping("/difficulty/{level}")
    public ResponseEntity<ApiResponse<List<Card>>> getCardsByDifficulty(
            @PathVariable DifficultyLevel level) {
        List<Card> cards = cardService.getCardsByDifficulty(level);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Cards retrieved successfully",
                cards,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }
}
