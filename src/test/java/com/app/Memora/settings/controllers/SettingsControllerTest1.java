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

class SettingsControllerTest1 {

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

        // Mocking the getCurrentUser method to return testUser
        when(userService.getCurrentUser()).thenReturn(testUser);
    }

    @Test
    void getUserSettings_Success() {
        when(settingsService.getUserSettings(1L)).thenReturn(testSettings);

        ResponseEntity<ApiResponse<Settings>> response = settingsController.getUserSettings();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(testSettings, response.getBody().getData());
        verify(settingsService, times(1)).getUserSettings(1L);
    }

    @Test
    void toggleSpacedRepetition_Enable() {
        doNothing().when(settingsService).toggleSpacedRepetition(1L, true);

        ResponseEntity<ApiResponse<Void>> response = settingsController.toggleSpacedRepetition(true);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        verify(settingsService, times(1)).toggleSpacedRepetition(1L, true);
    }

    @Test
    void toggleSpacedRepetition_Disable() {
        doNothing().when(settingsService).toggleSpacedRepetition(1L, false);

        ResponseEntity<ApiResponse<Void>> response = settingsController.toggleSpacedRepetition(false);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        verify(settingsService, times(1)).toggleSpacedRepetition(1L, false);
    }
}