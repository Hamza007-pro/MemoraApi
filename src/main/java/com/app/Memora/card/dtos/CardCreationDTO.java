package com.app.Memora.card.dtos;

import com.app.Memora.enums.DifficultyLevel;
import lombok.Data;

@Data
public class CardCreationDTO {
    private DifficultyLevel difficultyLevel;
    private Long contentId;
}
