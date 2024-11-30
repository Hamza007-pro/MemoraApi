package com.app.Memora.enroll.entities;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.progressTraking.entities.ProgressDeck;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "enrolls")
public class Enroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startingDate;
    private LocalDate endDate;
    private int progressPercentage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @OneToOne(cascade = CascadeType.ALL)
    private ProgressDeck progressDeck;
}
