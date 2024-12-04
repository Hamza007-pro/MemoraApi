package com.app.Memora.deck.entities;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.card.entities.Card;
import com.app.Memora.categorie.entities.Category;
import com.app.Memora.enroll.entities.Enroll;
import com.app.Memora.store.entities.Store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String image;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "deck", fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "deck_categories",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL)
    private List<Enroll> enrollments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.of(2024, 11, 30, 17, 34, 50);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.of(2024, 11, 30, 17, 34, 50);
    }
}
