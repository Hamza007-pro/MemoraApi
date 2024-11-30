package com.app.Memora.enroll.services;

import com.app.Memora.authentication.entities.User;
import com.app.Memora.authentication.services.UserService;
import com.app.Memora.deck.entities.Deck;
import com.app.Memora.deck.repositories.DeckRepository;
import com.app.Memora.enroll.entities.Enroll;
import com.app.Memora.enroll.repositories.EnrollRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.progressTraking.entities.ProgressDeck;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService {
    private final EnrollRepository enrollRepository;
    private final UserService userService;
    private final DeckRepository deckRepository;



    @Override
    @Transactional
    public Enroll enrollUserInDeck(Long userId, Long deckId) {
        log.info("Enrolling user {} in deck {}", userId, deckId);

        if (enrollRepository.findByUserIdAndDeckId(userId, deckId).isPresent()) {
            throw new IllegalStateException("User already enrolled in this deck");
        }

        User user = userService.getUserById(userId);
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found"));

        Enroll enroll = new Enroll();
        enroll.setUser(user);
        enroll.setDeck(deck);
        enroll.setStartingDate(LocalDate.now());
        enroll.setProgressPercentage(0);

        ProgressDeck progressDeck = new ProgressDeck();
        progressDeck.setScore(0);
        progressDeck.setLastReviewDate(LocalDateTime.now());
        enroll.setProgressDeck(progressDeck);

        return enrollRepository.save(enroll);
    }

    @Override
    public void unenrollUserFromDeck(Long userId, Long deckId) {

    }

    @Override
    public List<Enroll> getUserEnrollments(Long userId) {
        return null;
    }

    @Override
    public void updateProgress(Long enrollId, int progress) {

    }

    @Override
    public List<Deck> getRecommendedDecks(Long userId) {
        return null;
    }

    // Other methods implementation...
}
