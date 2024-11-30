package com.app.Memora.settings.controllers;

import com.app.Memora.authentication.services.UserService;
import com.app.Memora.settings.entities.Settings;
import com.app.Memora.settings.services.SettingsService;
import com.app.Memora.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
@Slf4j
public class SettingsController {
    private final SettingsService settingsService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<Settings>> getUserSettings() {
        Settings settings = settingsService.getUserSettings(userService.getCurrentUser().getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Settings retrieved", settings));
    }

    @PutMapping("/spaced-repetition")
    public ResponseEntity<ApiResponse<Void>> toggleSpacedRepetition(@RequestParam boolean enabled) {
        settingsService.toggleSpacedRepetition(userService.getCurrentUser().getId(), enabled);
        return ResponseEntity.ok(new ApiResponse<>(true, "Spaced repetition setting updated", null));
    }
}