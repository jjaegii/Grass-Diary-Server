package chzzk.grassdiary.domain.member.dto;

public record RewardHistoryDTO (
        Long historyId,
        String rewardType,
        int rewardPoint,
        String rewardedDate
){
}
