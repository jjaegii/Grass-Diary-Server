package chzzk.grassdiary.service;

import chzzk.grassdiary.domain.reward.RewardHistory;
import chzzk.grassdiary.domain.reward.RewardHistoryRepository;
import chzzk.grassdiary.web.dto.member.RewardHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardService {
    private final RewardHistoryRepository rewardHistoryRepository;

    public List<RewardHistoryDTO> getRewardHistory(Long memberId) {
        List<RewardHistory> rewardHistory = rewardHistoryRepository.findByMemberId(memberId);
        return rewardHistory.stream().map(history ->
                new RewardHistoryDTO(
                        history.getId(),
                        history.getRewardType().getName(),
                        history.getRewardPoint(),
                        makeHistoryStamp(history.getCreatedAt())
                )).toList();
    }

    private String makeHistoryStamp(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
