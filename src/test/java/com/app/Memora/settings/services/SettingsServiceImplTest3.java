package com.app.Memora.settings.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.settings.entities.Settings;
import com.app.Memora.settings.repositories.SettingsRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingsServiceImplTest3 {

    @Mock
    private SettingsRepository settingsRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private SettingsServiceImpl settingsService;

    private Settings testSettings;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testSettings = new Settings();
        testSettings.setId(1L);
        testSettings.setSpacedRepetitionEnabled(true);
    }

    @Test
    void updateSettings_Success() {
        when(settingsRepository.findById(1L)).thenReturn(Optional.of(testSettings));
        when(settingsRepository.save(testSettings)).thenReturn(testSettings);

        Settings result = settingsService.updateSettings(testSettings);

        assertNotNull(result);
        assertTrue(result.isSpacedRepetitionEnabled());
        verify(settingsRepository, times(1)).findById(1L);
        verify(settingsRepository, times(1)).save(testSettings);
    }

    @Test
    void updateSettings_NotFound() {
        when(settingsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            settingsService.updateSettings(testSettings);
        });

        verify(settingsRepository, times(1)).findById(1L);
        verify(settingsRepository, times(0)).save(testSettings);
    }
}