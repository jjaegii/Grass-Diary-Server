package chzzk.grassdiary.web.dto.diary;

import chzzk.grassdiary.domain.diary.Diary;
import chzzk.grassdiary.domain.diary.tag.TagList;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Getter;

@Getter
public class DiaryResponseDTO {
    private final Long id;
    private final Long memberId;
    private final String content;
    private final Boolean isPrivate;
    private final Boolean hasImage;
    private final Boolean hasTag;
    private final String createdDate;
    private final String createdAt;
    private final List<TagList> tags;
    private final Float transparency;
    private final Integer likeCount;
    private final boolean isLikedByLogInMember;

    public DiaryResponseDTO(Diary diary, List<TagList> tags, boolean isLikedByLogInMember) {
        this.id = diary.getId();
        this.memberId = diary.getMember().getId();
        this.content = diary.getContent();
        this.isPrivate = diary.getIsPrivate();
        this.hasImage = diary.getHasImage();
        this.hasTag = diary.getHasTag();
        this.createdDate = diary.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일"));
        this.createdAt = diary.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.tags = tags;
        this.transparency = diary.getConditionLevel().getTransparency();
        this.likeCount = diary.getDiaryLikes().size();
        this.isLikedByLogInMember = isLikedByLogInMember;
    }
}
