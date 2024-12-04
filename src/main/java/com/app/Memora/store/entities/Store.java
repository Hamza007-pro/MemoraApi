package com.app.Memora.store.entities;

import com.app.Memora.deck.entities.Deck;
import com.app.Memora.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    // One store has many decks
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "store")
    private List<Deck> decks;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.of(2024, 11, 30, 17, 34, 50);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.of(2024, 11, 30, 17, 34, 50);
    }
}
