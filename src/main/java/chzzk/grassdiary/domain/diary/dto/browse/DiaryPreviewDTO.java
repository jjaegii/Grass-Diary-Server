package chzzk.grassdiary.domain.diary.dto.browse;

import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.service.DiaryService;
import chzzk.grassdiary.domain.image.dto.ImageDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;

public record DiaryPreviewDTO(
        Long diaryId,
        String content,
        int diaryLikeCount,
        int commentCount,
        float transparency,
        Long memberId,
        String nickname,
        String createdAt,
        List<ImageDTO> image
    ) {

    public static List<DiaryPreviewDTO> of(Page<Diary> diaries, Function<Long, List<ImageDTO>> imageLoader) {
        return diaries.stream()
                .map(d -> toPreviewDiary(d, imageLoader.apply(d.getId())))
                .toList();
    }

    public static DiaryPreviewDTO toPreviewDiary(Diary diary, List<ImageDTO> images) {
        return new DiaryPreviewDTO(
                diary.getId(),
                trimContent(diary.getContent()),
                diary.getDiaryLikes().size(),
                diary.getComments().size(),
                diary.getConditionLevel().getTransparency(),
                diary.getMember().getId(),
                diary.getMember().getNickname(),
                diary.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                images
        );
    }

    private static String trimContent(String content) {
        return Optional.ofNullable(content)
                .map(c -> c.length() > 250 ? c.substring(0, 250) + "..." : c)
                .orElse("");
    }

}
