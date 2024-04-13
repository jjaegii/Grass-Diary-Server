package chzzk.grassdiary.web.dto.member;

public record RewardHistoryDTO (
        Long historyId,
        String rewardType,
        int rewardPoint,
        String rewardedDate
){
}
