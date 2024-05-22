package chzzk.grassdiary.domain.diary.dto;

import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.tag.TagList;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record DiaryDTO(
        Long diaryId,
        String content,
        List<TagList> tags,
        Float transparency,
        Boolean isPrivate,
        Integer likeCount,
        String createdDate,
        String createdAt
) {
    public static DiaryDTO from(Diary diary, List<TagList> tags) {
        return new DiaryDTO(
                diary.getId(),
                diary.getContent(),
                tags,
                diary.getConditionLevel().getTransparency(),
                diary.getIsPrivate(),
                diary.getDiaryLikes().size(),
                diary.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                diary.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
