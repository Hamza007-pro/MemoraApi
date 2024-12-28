package com.app.Memora.enroll.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.enroll.entities.Enroll;
import com.app.Memora.enroll.repositories.EnrollRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollServiceImplTest {

    @Mock
    private EnrollRepository enrollRepository;

    @Mock
    private UserService userService;

    @Mock
    private DeckRepository deckRepository;

    @InjectMocks
    private EnrollServiceImpl enrollService;

    private User testUser;
    private Deck testDeck;
    private Enroll testEnroll;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);

        testDeck = new Deck();
        testDeck.setId(1L);

        testEnroll = new Enroll();
        testEnroll.setUser(testUser);
        testEnroll.setDeck(testDeck);
        testEnroll.setStartingDate(LocalDate.now());
        testEnroll.setProgressPercentage(0);
    }

    @Test
    void enrollUserInDeck_Success() {
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(deckRepository.findById(1L)).thenReturn(Optional.of(testDeck));
        when(enrollRepository.findByUserIdAndDeckId(1L, 1L)).thenReturn(Optional.empty());
        when(enrollRepository.save(any(Enroll.class))).thenReturn(testEnroll);

        Enroll result = enrollService.enrollUserInDeck(1L, 1L);

        assertNotNull(result);
        assertEquals(testUser, result.getUser());
        assertEquals(testDeck, result.getDeck());
        assertEquals(LocalDate.now(), result.getStartingDate());
        assertEquals(0, result.getProgressPercentage());
        verify(enrollRepository, times(1)).save(any(Enroll.class));
    }

    @Test
    void enrollUserInDeck_UserAlreadyEnrolled() {
        when(enrollRepository.findByUserIdAndDeckId(1L, 1L)).thenReturn(Optional.of(testEnroll));

        assertThrows(IllegalStateException.class, () -> {
            enrollService.enrollUserInDeck(1L, 1L);
        });

        verify(enrollRepository, times(0)).save(any(Enroll.class));
    }

    @Test
    void enrollUserInDeck_DeckNotFound() {
        when(enrollRepository.findByUserIdAndDeckId(1L, 1L)).thenReturn(Optional.empty());
        when(deckRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            enrollService.enrollUserInDeck(1L, 1L);
        });

        verify(enrollRepository, times(0)).save(any(Enroll.class));
    }

    @Test
    void unenrollUserFromDeck_Success() {
        when(enrollRepository.findByUserIdAndDeckId(1L, 1L)).thenReturn(Optional.of(testEnroll));

        enrollService.unenrollUserFromDeck(1L, 1L);

        verify(enrollRepository, times(1)).findByUserIdAndDeckId(1L, 1L);
        verify(enrollRepository, times(1)).delete(testEnroll);
    }

    @Test
    void unenrollUserFromDeck_EnrollmentNotFound() {
        when(enrollRepository.findByUserIdAndDeckId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            enrollService.unenrollUserFromDeck(1L, 1L);
        });

        verify(enrollRepository, times(1)).findByUserIdAndDeckId(1L, 1L);
        verify(enrollRepository, times(0)).delete(any(Enroll.class));
    }
}