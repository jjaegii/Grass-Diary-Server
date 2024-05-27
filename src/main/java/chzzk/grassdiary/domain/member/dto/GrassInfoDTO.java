package chzzk.grassdiary.domain.member.dto;

import chzzk.grassdiary.domain.diary.entity.Diary;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class GrassInfoDTO {
    List<GrassInfo> grassList;
    String colorRGB;

    public GrassInfoDTO(List<Diary> diaryHistory, String rgb) {
        grassList = diaryHistory.stream()
                .map(diary -> new GrassInfo(
                        diary.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        diary.getConditionLevel().getTransparency()))
                .collect(Collectors.toList());
        colorRGB = rgb;
    }
}
