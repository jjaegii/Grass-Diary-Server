package chzzk.grassdiary.domain.reward;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RewardHistoryDAO extends JpaRepository<RewardHistory, Long> {
    List<RewardHistory> findByMemberId(Long memberId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RewardHistory r WHERE r.member.id = :memberId")
    void deleteAllByMemberId(Long memberId);
}
