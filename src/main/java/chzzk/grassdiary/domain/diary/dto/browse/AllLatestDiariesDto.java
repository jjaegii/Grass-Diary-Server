package chzzk.grassdiary.domain.diary.dto.browse;

import java.util.List;

public record AllLatestDiariesDto(
        LatestMetaDto meta,
        List<DiaryPreviewDTO> diaries
) {
    public static AllLatestDiariesDto of(List<DiaryPreviewDTO> diaries, boolean hasMore) {
        return new AllLatestDiariesDto(
                new LatestMetaDto(diaries.size(), hasMore),
                diaries
        );
    }
}
