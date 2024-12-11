package com.app.Memora.card.dtos;

import com.app.Memora.enums.DifficultyLevel;
import lombok.Data;

@Data
public class CardCreationDTO {
    private Long deckId;
    private DifficultyLevel difficultyLevel;
    private Long contentId;
}
