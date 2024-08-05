package chzzk.grassdiary.domain.diary.dto;

import chzzk.grassdiary.domain.color.ConditionLevel;
import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.member.entity.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiarySaveRequestDTO {
    private String content;
    private Boolean isPrivate;
    private Boolean hasTag;
    private ConditionLevel conditionLevel;
    private List<String> hashtags;
    private Long imageId;

    // DTO -> Entity
    public Diary toEntity(Member member) {
        return Diary.builder()
                .member(member)
                .content(content)
                .isPrivate(isPrivate)
                .hasTag(hasTag)
                .conditionLevel(conditionLevel)
                .build();
    }
}
