package com.app.Memora.deck.dtos;

import com.app.Memora.card.dtos.CardReadDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DeckReadDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CardReadDTO> cards;
    private List<Long> categoryIds;
    private List<Long> enrollmentIds;
}
