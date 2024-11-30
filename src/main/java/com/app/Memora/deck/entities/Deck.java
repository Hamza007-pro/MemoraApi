package com.app.Memora.deck.entities;

import com.app.Memora.card.entities.Card;
import com.app.Memora.categorie.entities.Category;
import com.app.Memora.enroll.entities.Enroll;
import com.app.Memora.store.entities.Store;

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

    @OneToOne(cascade = CascadeType.ALL)
    private Store store;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL)
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
}
