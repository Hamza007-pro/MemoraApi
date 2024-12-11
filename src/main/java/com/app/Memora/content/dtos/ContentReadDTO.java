package com.app.Memora.content.dtos;

import com.app.Memora.answer.dtos.AnswerDTO;
import com.app.Memora.question.dtos.QuestionDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentReadDTO {
    private Long id;
    private String image;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private QuestionDTO question;
    private AnswerDTO answer;
}
