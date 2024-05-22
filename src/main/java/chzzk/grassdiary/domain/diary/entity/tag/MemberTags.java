package chzzk.grassdiary.domain.diary.entity.tag;

import chzzk.grassdiary.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Entity
public class MemberTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberTags_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagList tagList;

    @ColumnDefault("0")
    private Integer memberTagUsageCount;

    @Builder
    public MemberTags(Member member, TagList tagList) {
        this.member = member;
        this.tagList = tagList;
        this.memberTagUsageCount = 0;
    }

    public void incrementCount() {
        memberTagUsageCount += 1;
    }

    public void decrementCount() {
        memberTagUsageCount -= 1;
    }
}