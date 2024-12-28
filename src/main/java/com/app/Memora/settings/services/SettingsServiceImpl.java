package com.app.Memora.settings.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.settings.entities.Settings;
import com.app.Memora.settings.repositories.SettingsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {
    private final SettingsRepository settingsRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Settings createSettings(Settings settings) {
        log.info("Creating new settings for user: {}", userService.getCurrentUser().getId());
        return settingsRepository.save(settings);
    }

    @Override
    public Settings updateSettings(Settings settings) {
        return settingsRepository.findById(settings.getId())
                .map(existingSettings -> {
                    existingSettings.setSpacedRepetitionEnabled(settings.isSpacedRepetitionEnabled());
                    // Update other fields as necessary
                    return settingsRepository.save(existingSettings);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Settings not found"));
    }

    @Override
    public Settings getUserSettings(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return user.getSettings();
    }

    @Override
    @Transactional
    public void toggleSpacedRepetition(Long userId, boolean enabled) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        Settings settings = user.getSettings();
        settings.setSpacedRepetitionEnabled(enabled);
        settingsRepository.save(settings);
    }

    @Override
    public List<Settings> getUsersWithSpacedRepetition() {
        return null;
    }

    // Other methods implementation...
}