package chzzk.grassdiary.web.dto.share;

import chzzk.grassdiary.domain.diary.Diary;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.data.domain.Page;

public record Top10DiariesDto(
        Long diaryId,
        String diaryContent,
        int diaryLikeCount,
        Long memberId,
        String nickname,
        String createdAt) {

    public static List<Top10DiariesDto> of(Page<Diary> diaries) {
        return diaries.stream()
                .map(d -> new Top10DiariesDto(
                        d.getId(),
                        d.getContent(),
                        d.getDiaryLikes().size(),
                        d.getMember().getId(),
                        d.getMember().getNickname(),
                        d.getCreatedAt()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )).toList();
    }
}
