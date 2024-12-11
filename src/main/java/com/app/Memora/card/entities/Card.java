package com.app.Memora.card.entities;

import com.app.Memora.content.entities.Content;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.enums.DifficultyLevel;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficultyLevel;

    @OneToOne(cascade = CascadeType.ALL)
    private Content content;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

//    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
//    private List<ProgressCard> progressCards = new ArrayList<>();
}
