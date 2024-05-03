package chzzk.grassdiary.web.dto.diary;

import chzzk.grassdiary.web.dto.member.GrassInfoDTO;

public record CountAndMonthGrassDTO(
        Integer totalCount,
        GrassInfoDTO grassInfoDTO,
        Integer thisMonthCount
) {
}
