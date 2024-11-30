package com.app.Memora.content.entities;

import com.app.Memora.answer.entities.Answer;
import com.app.Memora.enums.Status;
import com.app.Memora.question.entities.Question;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "contents")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    private LocalDateTime dateModified;

    @OneToOne(cascade = CascadeType.ALL)
    private Question question;

    @OneToOne(cascade = CascadeType.ALL)
    private Answer answer;

    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModified = LocalDateTime.now();
    }
}
