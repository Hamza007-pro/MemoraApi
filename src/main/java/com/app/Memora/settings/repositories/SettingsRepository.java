package com.app.Memora.settings.repositories;

import com.app.Memora.settings.entities.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
    List<Settings> findBySpacedRepetitionEnabled(boolean enabled);
}
