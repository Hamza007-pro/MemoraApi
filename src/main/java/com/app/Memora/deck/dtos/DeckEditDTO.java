package com.app.Memora.deck.dtos;

import lombok.Data;

@Data
public class DeckEditDTO {
    private String name;
    private String description;
    private String image;
    private boolean isPublic;
}
