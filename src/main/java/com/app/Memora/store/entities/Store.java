package com.app.Memora.store.entities;

import com.app.Memora.deck.entities.Deck;
import com.app.Memora.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "store")
    private Deck deck;
}
