package chzzk.grassdiary.domain.member.entity;

import chzzk.grassdiary.domain.base.BaseCreatedTimeEntity;
import chzzk.grassdiary.domain.member.entity.color.ColorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // DBTest에서 임시 사용
@NoArgsConstructor
@Entity
public class MemberPurchasedColor extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberPurchasedColor_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "colorCode_id")
    private ColorCode colorCode;
}
