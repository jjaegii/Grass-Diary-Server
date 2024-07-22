package chzzk.grassdiary.domain.diary.dto;

import chzzk.grassdiary.domain.member.dto.GrassInfoDTO;

public record CountAndMonthGrassDTO(
        Integer totalCount,
        GrassInfoDTO grassInfoDTO,
        Integer thisMonthCount
) {
}
