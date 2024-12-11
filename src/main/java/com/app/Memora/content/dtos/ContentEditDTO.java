package com.app.Memora.content.dtos;

import lombok.Data;

@Data
public class ContentEditDTO {
    private Long id;
    private String image;
    private Long questionId;
    private Long answerId;
}
