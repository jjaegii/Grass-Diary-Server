package chzzk.grassdiary.domain.diary;

import chzzk.grassdiary.domain.base.BaseCreatedTimeEntity;
import chzzk.grassdiary.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // DBTest에서 임시 사용
@NoArgsConstructor
@Entity
@Table(name = "diary_like", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "diary_id"})
})
public class DiaryLike extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diaryLike_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    public DiaryLike(Member member, Diary diary) {
        this.member = member;
        this.diary = diary;
    }
}
