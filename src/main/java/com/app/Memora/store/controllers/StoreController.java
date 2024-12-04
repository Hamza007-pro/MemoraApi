package com.app.Memora.store.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.store.services.StoreService;
import com.app.Memora.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    @PostMapping("/decks/{deckId}/submit")
    public ResponseEntity<ApiResponse<Void>> submitDeckForReview(@PathVariable Long deckId) {
        log.info("Submitting deck {} for review by: {}", deckId, userService.getCurrentUser());
        storeService.submitDeckForReview(deckId , userService.getCurrentUser());
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Deck submitted for review",
                null,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @PutMapping("/decks/{deckId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> approveDeck(@PathVariable Long deckId) {
        storeService.approveDeck(deckId);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Deck approved successfully",
                null,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @PutMapping("/decks/{deckId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> rejectDeck(@PathVariable Long deckId) {
        storeService.rejectDeck(deckId);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Deck rejected",
                null,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }

    @GetMapping("/decks/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Deck>>> getPendingDecks() {
        List<Deck> pendingDecks = storeService.getPendingDecks();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Pending decks retrieved",
                pendingDecks,
                LocalDateTime.of(2024, 11, 30, 15, 48, 6)
        ));
    }
}
