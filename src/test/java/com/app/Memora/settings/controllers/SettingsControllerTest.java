package com.app.Memora.settings.controllers;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.settings.entities.Settings;
import com.app.Memora.settings.services.SettingsService;
import com.app.Memora.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingsControllerTest {

    @Mock
    private SettingsService settingsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SettingsController settingsController;

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
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(settingsService.getUserSettings(1L)).thenReturn(testSettings);

        ResponseEntity<ApiResponse<Settings>> response = settingsController.getUserSettings();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(testSettings, response.getBody().getData());
        verify(settingsService, times(1)).getUserSettings(1L);
    }
}