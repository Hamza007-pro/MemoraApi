package com.app.Memora.enroll.repositories;

import com.app.Memora.enroll.entities.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    List<Enroll> findByUserId(Long userId);
    List<Enroll> findByDeckId(Long deckId);
    Optional<Enroll> findByUserIdAndDeckId(Long userId, Long deckId);

    @Query("SELECT e FROM Enroll e WHERE e.user.id = :userId AND e.progressPercentage < 100")
    List<Enroll> findIncompleteEnrollmentsByUserId(Long userId);
}
