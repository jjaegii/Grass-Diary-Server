package chzzk.grassdiary.domain.diary.entity.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TodayQuestionDAO extends JpaRepository<TodayQuestion, Long> {
    @Query("SELECT t FROM TodayQuestion t WHERE t.createdAt >= :startOfDay AND t.createdAt <= :endOfDay")
    TodayQuestion findByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
