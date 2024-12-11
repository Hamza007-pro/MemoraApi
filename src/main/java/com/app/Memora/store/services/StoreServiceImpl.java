package com.app.Memora.store.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.deck.services.DeckService;
import com.app.Memora.enums.Status;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.store.dtos.StoreReadDTO;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final DeckService deckService;

    @Override
    public Store createStore(Store store) {
        log.info("Creating new store: {}", store.getName());
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Long id, Store storeDetails) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        store.setName(storeDetails.getName());
        store.setDescription(storeDetails.getDescription());
        return storeRepository.save(store);
    }

    @Override
    public void deleteStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        storeRepository.delete(store);
    }

    @Override
    public StoreReadDTO convertToReadDTO(Store store) {
        StoreReadDTO storeReadDTO = new StoreReadDTO();
        storeReadDTO.setId(store.getId());
        storeReadDTO.setName(store.getName());
        storeReadDTO.setDescription(store.getDescription());
        storeReadDTO.setCreatedAt(store.getCreatedAt());
        storeReadDTO.setUpdatedAt(store.getUpdatedAt());
        storeReadDTO.setDecks(store.getDecks().stream()
                .map(deckService::convertToReadDTO)
                .collect(Collectors.toList()));
        return storeReadDTO;
    }

    @Override
    public Optional<Object> getStoreById(Long id) {
        return Optional.of(storeRepository.findById(id));
    }

    // Other methods implementation...
}
