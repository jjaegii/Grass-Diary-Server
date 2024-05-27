package chzzk.grassdiary.domain.reward;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardHistoryDAO extends JpaRepository<RewardHistory, Long> {
    List<RewardHistory> findByMemberId(Long memberId);
}
