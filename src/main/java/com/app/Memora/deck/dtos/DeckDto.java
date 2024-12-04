package com.app.Memora.deck.dtos;

import lombok.Data;

@Data
public class DeckDto {
    private Long id;
    private String name;
    private String description;
    private boolean isPublic;
    private String createdBy;
    private String storeName;
}
