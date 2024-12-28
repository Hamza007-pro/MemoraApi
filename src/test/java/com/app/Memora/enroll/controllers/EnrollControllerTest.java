package com.app.Memora.enroll.controllers;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.enroll.entities.Enroll;
import com.app.Memora.enroll.services.EnrollService;
import com.app.Memora.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EnrollControllerTest {

    @Mock
    private EnrollService enrollService;

    @Mock
    private UserService userService;

    @InjectMocks
    private EnrollController enrollController;

    private MockMvc mockMvc;
    private User testUser;
    private Enroll testEnroll;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(enrollController).build();

        testUser = new User();
        testUser.setId(1L);

        testEnroll = new Enroll();
        testEnroll.setId(1L);
        testEnroll.setUser(testUser);
    }

    @Test
    void enrollInDeck_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(enrollService.enrollUserInDeck(1L, 1L)).thenReturn(testEnroll);

        mockMvc.perform(post("/api/enrollments/decks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Enrolled successfully"))
                .andExpect(jsonPath("$.data.id").value(1L));

        verify(enrollService, times(1)).enrollUserInDeck(1L, 1L);
    }

    @Test
    void unenrollFromDeck_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(testUser);
        doNothing().when(enrollService).unenrollUserFromDeck(1L, 1L);

        mockMvc.perform(delete("/api/enrollments/decks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Unenrolled successfully"));

        verify(enrollService, times(1)).unenrollUserFromDeck(1L, 1L);
    }
}