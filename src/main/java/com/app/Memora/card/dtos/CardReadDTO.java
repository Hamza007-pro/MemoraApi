package com.app.Memora.card.dtos;

import com.app.Memora.content.dtos.ContentReadDTO;
import com.app.Memora.deck.dtos.DeckReadDTO;
import com.app.Memora.enums.DifficultyLevel;
import lombok.Data;

import java.util.List;

@Data
public class CardReadDTO {
    private Long id;
    private DifficultyLevel difficultyLevel;
    private ContentReadDTO content;
    private DeckReadDTO deck;
    private List<Long> progressCardIds;
}
