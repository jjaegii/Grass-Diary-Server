package chzzk.grassdiary.domain.diary.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TodayQuestionRepository extends JpaRepository<TodayQuestion, Long> {
    @Query("SELECT t FROM TodayQuestion t WHERE t.createdAt >= :startOfDay AND t.createdAt <= :endOfDay")
    TodayQuestion findByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
