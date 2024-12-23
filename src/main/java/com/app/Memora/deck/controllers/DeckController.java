package com.app.Memora.deck.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.card.entities.Card;
import com.app.Memora.card.services.CardService;
import com.app.Memora.categorie.services.CategoryService;
import com.app.Memora.deck.dtos.DeckCreationDTO;
import com.app.Memora.deck.dtos.DeckEditDTO;
import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.services.DeckService;
import com.app.Memora.enroll.services.EnrollService;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.services.StoreService;
import com.app.Memora.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/decks")
@RequiredArgsConstructor
@Slf4j
public class DeckController {
    @Autowired
    private DeckService deckService;

    @GetMapping
    public List<DeckReadDTO> getAllDecks() {
        return deckService.getAllDecks().stream()
                .map(deckService::convertToReadDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeckReadDTO> getDeckById(@PathVariable Long id) {
        return deckService.getDeckById(id)
                .map(deckService::convertToReadDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{storeId}")
    public DeckReadDTO createDeck(@PathVariable Long storeId,@RequestBody DeckCreationDTO deckCreationDTO) {
        Deck deck = new Deck();
        deck.setName(deckCreationDTO.getName());
        deck.setDescription(deckCreationDTO.getDescription());
        deck.setImage(deckCreationDTO.getImage());
        deck.setPublic(deckCreationDTO.isPublic());
        Deck savedDeck = deckService.createDeck(storeId,deck);
        return deckService.convertToReadDTO(savedDeck);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeckReadDTO> updateDeck(@PathVariable Long id, @RequestBody DeckEditDTO deckEditDTO) {
        return deckService.getDeckById(id)
                .map(deck -> {
                    deck.setName(deckEditDTO.getName());
                    deck.setDescription(deckEditDTO.getDescription());
                    deck.setImage(deckEditDTO.getImage());
                    deck.setPublic(deckEditDTO.isPublic());
                    Deck updatedDeck = deckService.updateDeck(id, deck);
                    return ResponseEntity.ok(deckService.convertToReadDTO(updatedDeck));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long id) {
        try {
            deckService.deleteDeck(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    public List<DeckReadDTO> getUserDecks(){
        return deckService.getUserDecks().stream()
                .map(deckService::convertToReadDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/search/{query}")
    public List<DeckReadDTO> searchDecks(@PathVariable String query){
        return deckService.searchDecks(query).stream()
                .map(deckService::convertToReadDTO)
                .collect(Collectors.toList());
    }



}
