package chzzk.grassdiary.domain.member.dto;

import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.color.entity.ColorCode;

public record EquipColorResponseDTO(
        Long memberId,
        Long colorCodeId,
        String colorName,
        String rgb
) {
    public static EquipColorResponseDTO from(Member member) {
        ColorCode currentColorCode = member.getCurrentColorCode();
        return new EquipColorResponseDTO(
                member.getId(),
                currentColorCode.getId(),
                currentColorCode.getColorName(),
                currentColorCode.getRgb()
        );
    }
}
