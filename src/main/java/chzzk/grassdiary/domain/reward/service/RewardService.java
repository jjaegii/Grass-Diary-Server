package chzzk.grassdiary.domain.reward.service;

import chzzk.grassdiary.domain.member.dto.TotalRewardDTO;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.domain.reward.RewardHistory;
import chzzk.grassdiary.domain.reward.RewardHistoryDAO;
import chzzk.grassdiary.domain.member.dto.RewardHistoryDTO;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
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
    private final RewardHistoryDAO rewardHistoryDAO;
    private final MemberDAO memberDAO;

    public TotalRewardDTO findTotalRewardById(Long memberId) {
        memberDAO.findById(memberId)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));

        Integer rewardPoint = memberDAO.findRewardPointById(memberId);
        return new TotalRewardDTO(rewardPoint);
    }

    public List<RewardHistoryDTO> getRewardHistory(Long memberId) {
        List<RewardHistory> rewardHistory = rewardHistoryDAO.findByMemberId(memberId);
        return rewardHistory.stream().map(history ->
                new RewardHistoryDTO(
                        history.getId(),
                        history.getRewardType().getName(),
                        history.getRewardPoint(),
                        makeHistoryStamp(history.getCreatedAt())
                )).toList();
    }

    public void deleteAllRewardHistory(long memberId) {
        validateMemberExists(memberId);

        rewardHistoryDAO.deleteAllByMemberId(memberId);
    }

    private String makeHistoryStamp(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private void validateMemberExists(Long memberId) {
        memberDAO.findById(memberId)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));
    }
}
