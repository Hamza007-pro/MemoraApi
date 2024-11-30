package com.app.Memora.progressTraking.services;

import com.app.Memora.card.entities.Card;
import com.app.Memora.progressTraking.entities.ProgressCard;
import com.app.Memora.progressTraking.entities.ProgressDeck;

import java.util.List;

public interface ProgressTrackingService {
    void updateCardProgress(Long cardId, boolean correct);
    List<ProgressCard> getCardProgress(Long deckId);
    ProgressDeck getDeckProgress(Long enrollId);
    List<Card> getReviewDueCards(Long userId);
}
