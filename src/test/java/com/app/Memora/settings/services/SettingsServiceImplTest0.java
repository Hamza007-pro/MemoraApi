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

class SettingsServiceImplTest0 {

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
        testSettings.setSpacedRepetitionEnabled(true);

        testUser = new User();
        testUser.setId(1L);
        testUser.setSettings(testSettings);
    }

    @Test
    void createSettings_Success() {
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(settingsRepository.save(testSettings)).thenReturn(testSettings);

        Settings result = settingsService.createSettings(testSettings);

        assertNotNull(result);
        assertTrue(result.isSpacedRepetitionEnabled());
        verify(settingsRepository, times(1)).save(testSettings);
    }
}