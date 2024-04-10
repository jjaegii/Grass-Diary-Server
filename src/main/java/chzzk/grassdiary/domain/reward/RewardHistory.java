package chzzk.grassdiary.domain.reward;

import chzzk.grassdiary.domain.base.BaseCreatedTimeEntity;
import chzzk.grassdiary.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RewardHistory extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rewardHistory_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private RewardType rewardType;

    private Integer rewardPoint;

    @Builder
    public RewardHistory(Member member, RewardType rewardType, Integer rewardPoint) {
        this.member = member;
        this.rewardType = rewardType;
        this.rewardPoint = rewardPoint;
    }
}
