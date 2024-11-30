package com.app.Memora.settings.services;

import com.app.Memora.settings.entities.Settings;

import java.util.List;

public interface SettingsService {
    Settings createSettings(Settings settings);
    Settings updateSettings(Settings settings);
    Settings getUserSettings(Long userId);
    void toggleSpacedRepetition(Long userId, boolean enabled);
    List<Settings> getUsersWithSpacedRepetition();
}
