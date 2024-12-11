package com.app.Memora.card.dtos;

import com.app.Memora.enums.DifficultyLevel;
import lombok.Data;

@Data
public class CardEditDTO {
    private Long id;
    private DifficultyLevel difficultyLevel;
    private Long contentId;
}
