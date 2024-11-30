package com.app.Memora.progressTraking.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.card.repositories.CardRepository;
import com.app.Memora.exceptions.ResourceNotFoundException;
import com.app.Memora.progressTraking.entities.ProgressCard;
import com.app.Memora.progressTraking.entities.ProgressDeck;
import com.app.Memora.progressTraking.repositories.ProgressCardRepository;
import com.app.Memora.progressTraking.repositories.ProgressDeckRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProgressTrackingServiceImpl implements ProgressTrackingService {
    private final ProgressCardRepository progressCardRepository;
    private final ProgressDeckRepository progressDeckRepository;
    private final CardRepository cardRepository;

    @Override
    @Transactional
    public void updateCardProgress(Long cardId, boolean correct) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        ProgressCard progress = progressCardRepository.findByCardId(cardId)
                .stream().findFirst()
                .orElseGet(() -> {
                    ProgressCard newProgress = new ProgressCard();
                    newProgress.setCard(card);
                    newProgress.setTotalAttempts(0);
                    newProgress.setCorrectAttempts(0);
                    newProgress.setScore(0);
                    return newProgress;
                });

        progress.setTotalAttempts(progress.getTotalAttempts() + 1);
        if (correct) {
            progress.setCorrectAttempts(progress.getCorrectAttempts() + 1);
        }
        progress.setScore(calculateScore(progress));

        progressCardRepository.save(progress);
    }

    @Override
    public List<ProgressCard> getCardProgress(Long deckId) {
        return null;
    }

    @Override
    public ProgressDeck getDeckProgress(Long enrollId) {
        return null;
    }

    @Override
    public List<Card> getReviewDueCards(Long userId) {
        return null;
    }

    private int calculateScore(ProgressCard progress) {
        if (progress.getTotalAttempts() == 0) return 0;
        return (int) ((progress.getCorrectAttempts() * 100.0) / progress.getTotalAttempts());
    }

    // Other methods implementation...
}
