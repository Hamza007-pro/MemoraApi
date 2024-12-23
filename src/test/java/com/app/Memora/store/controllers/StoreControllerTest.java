package com.app.Memora.store.controllers;

import com.app.Memora.store.dtos.StoreCreationDTO;
import com.app.Memora.store.dtos.StoreEditDTO;
import com.app.Memora.store.dtos.StoreReadDTO;
import com.app.Memora.store.entities.Store;
import com.app.Memora.store.services.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreControllerTest {

    @Mock
    private StoreService storeService;

    @InjectMocks
    private StoreController storeController;

    private Store store;
    private StoreReadDTO storeReadDTO;
    private StoreCreationDTO storeCreationDTO;
    private StoreEditDTO storeEditDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        store = new Store();
        store.setId(1L);
        store.setName("Test Store");
        store.setDescription("Test Description");
        store.setCreatedAt(now);
        store.setUpdatedAt(now);

        storeReadDTO = new StoreReadDTO();
        storeReadDTO.setId(1L);
        storeReadDTO.setName("Test Store");
        storeReadDTO.setDescription("Test Description");
        storeReadDTO.setCreatedAt(now);
        storeReadDTO.setUpdatedAt(now);

        storeCreationDTO = new StoreCreationDTO();
        storeCreationDTO.setName("Test Store");
        storeCreationDTO.setDescription("Test Description");

        storeEditDTO = new StoreEditDTO();
        storeEditDTO.setName("Updated Store");
        storeEditDTO.setDescription("Updated Description");
    }

    @Test
    void createStore_ReturnsCreatedStore() {
        // Arrange
        when(storeService.createStore(any(Store.class))).thenReturn(store);
        when(storeService.convertToReadDTO(store)).thenReturn(storeReadDTO);

        // Act
        StoreReadDTO result = storeController.createStore(storeCreationDTO);

        // Assert
        assertNotNull(result);
        assertEquals(storeReadDTO.getId(), result.getId());
        assertEquals(storeReadDTO.getName(), result.getName());
        assertEquals(storeReadDTO.getDescription(), result.getDescription());
        verify(storeService).createStore(any(Store.class));
        verify(storeService).convertToReadDTO(store);
    }

    @Test
    void updateStore_WhenStoreExists_ReturnsUpdatedStore() {
        // Arrange
        when(storeService.getStoreById(1L)).thenReturn(Optional.of(store));
        when(storeService.updateStore(eq(1L), any(Store.class))).thenReturn(store);
        when(storeService.convertToReadDTO(store)).thenReturn(storeReadDTO);

        // Act
        ResponseEntity<StoreReadDTO> response = storeController.updateStore(1L, storeEditDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(storeReadDTO, response.getBody());
        verify(storeService).getStoreById(1L);
        verify(storeService).updateStore(eq(1L), any(Store.class));
    }

    @Test
    void updateStore_WhenStoreDoesNotExist_ThrowsException() {
        // Arrange
        when(storeService.getStoreById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () ->
                storeController.updateStore(1L, storeEditDTO)
        );
        verify(storeService).getStoreById(1L);
        verify(storeService, never()).updateStore(any(Long.class), any(Store.class));
    }

    @Test
    void deleteStore_ReturnsNoContent() {
        // Arrange
        doNothing().when(storeService).deleteStore(1L);

        // Act
        ResponseEntity<Void> response = storeController.deleteStore(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(storeService).deleteStore(1L);
    }
}