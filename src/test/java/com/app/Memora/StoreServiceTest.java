package com.app.Memora;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.services.DeckService;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.store.dtos.StoreReadDTO;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.repositories.StoreRepository;
import com.app.Memora.store.services.StoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private DeckService deckService;

    @InjectMocks
    private StoreServiceImpl storeService;

    private Store testStore;
    private Deck testDeck;
    private DeckReadDTO testDeckReadDTO;

    @BeforeEach
    void setUp() {
        // Initialize test store
        testStore = new Store();
        testStore.setId(1L);
        testStore.setName("Test Store");
        testStore.setDescription("Test Description");
        testStore.setCreatedAt(LocalDateTime.now());
        testStore.setUpdatedAt(LocalDateTime.now());

        // Initialize test deck
        testDeck = new Deck();
        testDeck.setId(1L);
        testDeck.setName("Test Deck");
        testDeck.setStore(testStore);

        // Initialize test deck DTO
        testDeckReadDTO = new DeckReadDTO();
        testDeckReadDTO.setId(1L);
        testDeckReadDTO.setName("Test Deck");

        // Set up store decks
        List<Deck> decks = new ArrayList<>();
        decks.add(testDeck);
        testStore.setDecks(decks);
    }

    @Test
    void createStore_Success() {
        when(storeRepository.save(any(Store.class))).thenReturn(testStore);

        Store result = storeService.createStore(testStore);

        assertNotNull(result);
        assertEquals("Test Store", result.getName());
        assertEquals("Test Description", result.getDescription());
        verify(storeRepository).save(testStore);
    }

    @Test
    void updateStore_Success() {
        Store updateDetails = new Store();
        updateDetails.setName("Updated Store");
        updateDetails.setDescription("Updated Description");

        when(storeRepository.findById(1L)).thenReturn(Optional.of(testStore));
        when(storeRepository.save(any(Store.class))).thenReturn(testStore);

        Store result = storeService.updateStore(1L, updateDetails);

        assertNotNull(result);
        assertEquals("Updated Store", result.getName());
        assertEquals("Updated Description", result.getDescription());
        verify(storeRepository).findById(1L);
        verify(storeRepository).save(any(Store.class));
    }

    @Test
    void updateStore_NotFound() {
        Store updateDetails = new Store();
        when(storeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                storeService.updateStore(1L, updateDetails));
        verify(storeRepository).findById(1L);
        verify(storeRepository, never()).save(any(Store.class));
    }

    @Test
    void deleteStore_Success() {
        when(storeRepository.findById(1L)).thenReturn(Optional.of(testStore));
        doNothing().when(storeRepository).delete(testStore);

        storeService.deleteStore(1L);

        verify(storeRepository).findById(1L);
        verify(storeRepository).delete(testStore);
    }

    @Test
    void deleteStore_NotFound() {
        when(storeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                storeService.deleteStore(1L));
        verify(storeRepository).findById(1L);
        verify(storeRepository, never()).delete(any(Store.class));
    }

    @Test
    void convertToReadDTO_Success() {
        when(deckService.convertToReadDTO(any(Deck.class))).thenReturn(testDeckReadDTO);

        StoreReadDTO result = storeService.convertToReadDTO(testStore);

        assertNotNull(result);
        assertEquals(testStore.getId(), result.getId());
        assertEquals(testStore.getName(), result.getName());
        assertEquals(testStore.getDescription(), result.getDescription());
        assertEquals(testStore.getCreatedAt(), result.getCreatedAt());
        assertEquals(testStore.getUpdatedAt(), result.getUpdatedAt());
        assertFalse(result.getDecks().isEmpty());
        assertEquals(1, result.getDecks().size());
        verify(deckService).convertToReadDTO(testDeck);
    }

    @Test
    void getStoreById_Success() {
        when(storeRepository.findById(1L)).thenReturn(Optional.of(testStore));

        Optional<Object> result = storeService.getStoreById(1L);

        assertTrue(result.isPresent());
        verify(storeRepository).findById(1L);
    }

    @Test
    void getStoreById_NotFound() {
        when(storeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Object> result = storeService.getStoreById(1L);

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof Optional);
        assertFalse(((Optional<?>) result.get()).isPresent());
        verify(storeRepository).findById(1L);
    }
}
