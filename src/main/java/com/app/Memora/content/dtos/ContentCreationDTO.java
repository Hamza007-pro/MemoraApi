package com.app.Memora.content.dtos;

import lombok.Data;

@Data
public class ContentCreationDTO {
    private String image;
    private Long questionId;
    private Long answerId;
}
