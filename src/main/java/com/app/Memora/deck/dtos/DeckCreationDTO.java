package com.app.Memora.deck.dtos;

import com.app.Memora.categorie.entities.Category;
import lombok.Data;

import java.util.List;

@Data
public class DeckCreationDTO {
    private String name;
    private String description;
    private String image;
    private boolean isPublic;
    private Long storeId;
}
