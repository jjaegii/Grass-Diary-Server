package chzzk.grassdiary.domain.member.dto;

import chzzk.grassdiary.domain.member.entity.MemberPurchasedColor;

public record MemberPurchasedColorResponseDTO(
        Long id,
        Long memberId,
        Long colorCodeId,
        String colorName,
        String rgb
) {
    public MemberPurchasedColorResponseDTO(MemberPurchasedColor memberPurchasedColor) {
        this(
                memberPurchasedColor.getId(),
                memberPurchasedColor.getMember().getId(),
                memberPurchasedColor.getColorCode().getId(),
                memberPurchasedColor.getColorCode().getColorName(),
                memberPurchasedColor.getColorCode().getRgb()
        );
    }
}