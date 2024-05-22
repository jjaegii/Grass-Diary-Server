package chzzk.grassdiary.domain.diary.dto.browse;

public record LatestMetaDto(
        int count,
        boolean hasMore
) {
    public static LatestMetaDto of(int count, boolean hasMore) {
        return new LatestMetaDto(
                count,
                hasMore
        );
    }
}
