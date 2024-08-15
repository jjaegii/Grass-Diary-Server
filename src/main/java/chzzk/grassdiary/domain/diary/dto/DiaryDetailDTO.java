package chzzk.grassdiary.domain.diary.dto;

import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.tag.TagList;
import chzzk.grassdiary.domain.image.dto.ImageDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record DiaryDetailDTO(
        Long diaryId,
        Long memberId,
        String content,
        List<TagList> tags,
        Float transparency,
        Boolean isPrivate,
        Integer likeCount,
        Boolean isLikedByLogInMember,
        int commentCount,
        String createdDate,
        String createdAt,
        List<ImageDTO> image
) {
    public static DiaryDetailDTO from(Diary diary, List<TagList> tags, boolean isLikedByLogInMember, List<ImageDTO> image) {
        return new DiaryDetailDTO(
                diary.getId(),
                diary.getMember().getId(),
                diary.getContent(),
                tags,
                diary.getConditionLevel().getTransparency(),
                diary.getIsPrivate(),
                diary.getDiaryLikes().size(),
                isLikedByLogInMember,
                diary.getComments().size(),
                diary.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                diary.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm")),
                image
        );
    }
}
