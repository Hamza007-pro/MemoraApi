package com.app.Memora.deck.dtos;

import lombok.Data;

@Data
public class DeckCreationDTO {
    private String name;
    private String description;
    private String image;
    private boolean isPublic;
    private Long storeId;
}
