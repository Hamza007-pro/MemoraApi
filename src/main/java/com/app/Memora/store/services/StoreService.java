package com.app.Memora.store.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.store.dtos.StoreReadDTO;
import com.app.Memora.store.entities.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    Store createStore(Store store);
    Store updateStore(Long id, Store storeDetails);
    void deleteStore(Long id);
    StoreReadDTO convertToReadDTO(Store store);

    Optional<Object> getStoreById(Long id);
}
