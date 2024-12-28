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

class SettingsServiceImplTest2 {

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
    void getUserSettings_Success() {
        when(userService.getUserById(1L)).thenReturn(testUser);

        Settings result = settingsService.getUserSettings(1L);

        assertNotNull(result);
        assertEquals(testSettings, result);
        verify(userService, times(1)).getUserById(1L);
    }
}