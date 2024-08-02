package chzzk.grassdiary.domain.diary.dto.browse;

import chzzk.grassdiary.domain.diary.entity.Diary;
import java.util.List;

public record AllLatestDiariesDto(
        LatestMetaDto meta,
        List<DiaryPreviewDTO> diaries
) {
    public static AllLatestDiariesDto of(List<Diary> diaries, boolean hasMore) {
        return new AllLatestDiariesDto(
                LatestMetaDto.of(diaries.size(), hasMore),
                DiaryPreviewDTO.toLatestDiariesDto(diaries)
        );
    }
}
