package chzzk.grassdiary.web.dto.share;

import chzzk.grassdiary.domain.diary.Diary;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record LatestDiaryDto(
        Long diaryId,
        String content,
        int diaryLikeCount,
        Long memberId,
        String nickname,
        String createdAt) {

    public static List<LatestDiaryDto> toLatestDiariesDto(List<Diary> diaries) {
        return diaries.stream()
                .map(LatestDiaryDto::toLatestDiary)
                .toList();
    }

    private static LatestDiaryDto toLatestDiary(Diary diary) {
        return new LatestDiaryDto(
                diary.getId(),
                diary.getContent(),
                diary.getDiaryLikes().size(),
                diary.getMember().getId(),
                diary.getMember().getNickname(),
                diary.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

}
