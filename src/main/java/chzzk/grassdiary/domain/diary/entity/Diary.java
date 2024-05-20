package chzzk.grassdiary.domain.diary.entity;

import chzzk.grassdiary.domain.base.BaseTimeEntity;
import chzzk.grassdiary.domain.member.entity.color.ConditionLevel;
import chzzk.grassdiary.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Diary extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("true")
    private Boolean isPrivate;

    @OneToMany(mappedBy = "diary")
    private List<DiaryLike> diaryLikes = new ArrayList<>();

    @ColumnDefault("false")
    private Boolean hasImage;

    @ColumnDefault("false")
    private Boolean hasTag;

    @Enumerated(EnumType.STRING)
    private ConditionLevel conditionLevel;

    @Builder
    protected Diary(Member member, String content, Boolean isPrivate, Boolean hasImage,
                    Boolean hasTag, ConditionLevel conditionLevel) {
        this.member = member;
        this.content = content;
        this.isPrivate = isPrivate;
        this.hasImage = hasImage;
        this.hasTag = hasTag;
        this.conditionLevel = conditionLevel;
        this.setCreatedAt(LocalDateTime.now());
    }

    public void update(String content, Boolean isPrivate, Boolean hasImage, Boolean hasTag,
                       ConditionLevel conditionLevel) {
        this.content = content;
        this.isPrivate = isPrivate;
        this.hasImage = hasImage;
        this.hasTag = hasTag;
        this.conditionLevel = conditionLevel;
    }

    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }
}
