package com.app.Memora.store.dtos;

import com.app.Memora.deck.dtos.DeckReadDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StoreReadDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<DeckReadDTO> decks;
}
