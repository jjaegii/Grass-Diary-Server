package chzzk.grassdiary.domain.diary.dto;

import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.DiaryLike;
import chzzk.grassdiary.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryLikeRequestDTO {
    private Member member;
    private Diary diary;

    public DiaryLike toEntity(Diary diary, Member member) {
        DiaryLike diaryLike = DiaryLike.builder()
                .member(member)
                .diary(diary)
                .build();

        return diaryLike;
    }
}
