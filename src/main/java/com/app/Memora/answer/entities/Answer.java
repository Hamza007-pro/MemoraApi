package com.app.Memora.answer.entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;
}