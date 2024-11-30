package com.app.Memora.settings.entities;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean spacedRepetitionEnabled;

    @OneToOne(mappedBy = "settings")
    private User user;
}
