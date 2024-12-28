package com.app.Memora.settings.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.settings.entities.Settings;
import com.app.Memora.settings.repositories.SettingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingsServiceImplTest {

    @Mock
    private SettingsRepository settingsRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private SettingsServiceImpl settingsService;

    private User testUser;
    private Settings testSettings;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);

        testSettings = new Settings();
        testSettings.setId(1L);
        testSettings.setSpacedRepetitionEnabled(true);
        testUser.setSettings(testSettings);
    }

    @Test
    void createSettings_Success() {
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(settingsRepository.save(testSettings)).thenReturn(testSettings);

        Settings result = settingsService.createSettings(testSettings);

        assertNotNull(result);
        assertEquals(testSettings, result);
        verify(settingsRepository, times(1)).save(testSettings);
    }

    @Test
    void updateSettings_Success() {
        when(settingsRepository.findById(1L)).thenReturn(Optional.of(testSettings));
        when(settingsRepository.save(testSettings)).thenReturn(testSettings);

        Settings result = settingsService.updateSettings(testSettings);

        assertNotNull(result);
        assertEquals(testSettings, result);
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
        verify(settingsRepository, times(0)).save(any(Settings.class));
    }

    @Test
    void getUserSettings_Success() {
        when(userService.getUserById(1L)).thenReturn(testUser);

        Settings result = settingsService.getUserSettings(1L);

        assertNotNull(result);
        assertEquals(testSettings, result);
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getUserSettings_UserNotFound() {
        when(userService.getUserById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            settingsService.getUserSettings(1L);
        });

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void toggleSpacedRepetition_Success() {
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(settingsRepository.save(testSettings)).thenReturn(testSettings);

        settingsService.toggleSpacedRepetition(1L, false);

        assertFalse(testSettings.isSpacedRepetitionEnabled());
        verify(userService, times(1)).getUserById(1L);
        verify(settingsRepository, times(1)).save(testSettings);
    }

    @Test
    void toggleSpacedRepetition_UserNotFound() {
        when(userService.getUserById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            settingsService.toggleSpacedRepetition(1L, false);
        });

        verify(userService, times(1)).getUserById(1L);
        verify(settingsRepository, times(0)).save(any(Settings.class));
    }
    @Test
    void getUsersWithSpacedRepetition_NotImplemented() {
        assertNull(settingsService.getUsersWithSpacedRepetition());
    }
}