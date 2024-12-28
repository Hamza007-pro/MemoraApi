package com.app.Memora.settings.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.settings.entities.Settings;
import com.app.Memora.settings.repositories.SettingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingsServiceImplTest1 {

    @Mock
    private SettingsRepository settingsRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private SettingsServiceImpl settingsService;

    private Settings testSettings;
    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testSettings = new Settings();
        testSettings.setSpacedRepetitionEnabled(false);

        testUser = new User();
        testUser.setId(1L);
        testUser.setSettings(testSettings);
    }

    @Test
    void toggleSpacedRepetition_Enable() {
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(settingsRepository.save(testSettings)).thenReturn(testSettings);

        settingsService.toggleSpacedRepetition(1L, true);

        assertTrue(testSettings.isSpacedRepetitionEnabled());
        verify(settingsRepository, times(1)).save(testSettings);
    }

    @Test
    void toggleSpacedRepetition_Disable() {
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(settingsRepository.save(testSettings)).thenReturn(testSettings);

        settingsService.toggleSpacedRepetition(1L, false);

        assertFalse(testSettings.isSpacedRepetitionEnabled());
        verify(settingsRepository, times(1)).save(testSettings);
    }
}