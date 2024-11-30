package com.app.Memora.deck.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.entities.Card;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.services.DeckService;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/decks")
@RequiredArgsConstructor
@Slf4j
public class DeckController {
    private final DeckService deckService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Deck>> createDeck(@Valid @RequestBody Deck deck) {
        log.info("Creating new deck by: {}", userService.getCurrentUser());
        Deck createdDeck = deckService.createDeck(deck);
        return ResponseEntity.ok(new ApiResponse<>(true, "Deck created successfully", createdDeck));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Deck>>> getUserDecks() {
        List<Deck> decks = deckService.getUserDecks();
        return ResponseEntity.ok(new ApiResponse<>(true, "User decks retrieved", decks));
    }

    @PostMapping("/{deckId}/cards")
    public ResponseEntity<ApiResponse<Void>> addCardToDeck(@PathVariable Long deckId,
                                                           @Valid @RequestBody Card card) {
        deckService.addCardToDeck(deckId, card);
        return ResponseEntity.ok(new ApiResponse<>(true, "Card added to deck successfully", null));
    }
}
