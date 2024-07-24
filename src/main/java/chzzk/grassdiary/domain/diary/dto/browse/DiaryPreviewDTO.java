package chzzk.grassdiary.domain.diary.dto.browse;

import chzzk.grassdiary.domain.diary.entity.Diary;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.data.domain.Page;

public record DiaryPreviewDTO(
        Long diaryId,
        String diaryContent,
        int diaryLikeCount,
        Long memberId,
        String nickname,
        String createdAt) {

    public static List<DiaryPreviewDTO> of(Page<Diary> diaries) {
        return diaries.stream()
                .map(d -> new DiaryPreviewDTO(
                        d.getId(),
                        d.getContent(),
                        d.getDiaryLikes().size(),
                        d.getMember().getId(),
                        d.getMember().getNickname(),
                        d.getCreatedAt()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )).toList();
    }

    private static DiaryPreviewDTO toPreviewDiary(Diary diary) {
        return new DiaryPreviewDTO(
                diary.getId(),
                diary.getContent(),
                diary.getDiaryLikes().size(),
                diary.getMember().getId(),
                diary.getMember().getNickname(),
                diary.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    public static List<DiaryPreviewDTO> toLatestDiariesDto(List<Diary> diaries) {
        return diaries.stream()
                .map(DiaryPreviewDTO::toPreviewDiary)
                .toList();
    }

}
