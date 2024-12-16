package com.app.Memora.categorie.dtos;

import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.deck.entities.Deck;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryReadDTO {
    private Long id;
    private String name;
    private List<DeckReadDTO> decks = new ArrayList<>();
}
