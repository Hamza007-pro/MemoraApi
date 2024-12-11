package com.app.Memora.store.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.services.DeckService;
import com.app.Memora.store.dtos.StoreCreationDTO;
import com.app.Memora.store.dtos.StoreEditDTO;
import com.app.Memora.store.dtos.StoreReadDTO;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.services.StoreService;
import com.app.Memora.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private StoreService storeService;



    @PostMapping
    public StoreReadDTO createStore(@RequestBody StoreCreationDTO storeCreationDTO) {
        Store store = new Store();
        store.setName(storeCreationDTO.getName());
        store.setDescription(storeCreationDTO.getDescription());
        Store savedStore = storeService.createStore(store);
        return storeService.convertToReadDTO(savedStore);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreReadDTO> updateStore(@PathVariable Long id, @RequestBody StoreEditDTO storeEditDTO) {
        Store store = (Store) storeService.getStoreById(id).orElseThrow();
        store.setName(storeEditDTO.getName());
        store.setDescription(storeEditDTO.getDescription());
        Store updatedStore = storeService.updateStore(id, store);
        return ResponseEntity.ok(storeService.convertToReadDTO(updatedStore));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }
}
