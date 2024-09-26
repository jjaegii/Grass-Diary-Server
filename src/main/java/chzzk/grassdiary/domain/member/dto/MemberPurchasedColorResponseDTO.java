package chzzk.grassdiary.domain.member.dto;


import chzzk.grassdiary.domain.color.entity.ColorCode;
import chzzk.grassdiary.domain.member.entity.MemberPurchasedColor;

public record MemberPurchasedColorResponseDTO(
        Long id,
        String colorName,
        String rgb,
        int price
) {
    public static MemberPurchasedColorResponseDTO from(MemberPurchasedColor memberPurchasedColor) {
        ColorCode colorCode = memberPurchasedColor.getColorCode();
        return new MemberPurchasedColorResponseDTO(
                colorCode.getId(),
                colorCode.getColorName(),
                colorCode.getRgb(),
                colorCode.getPrice()
        );
    }

    public static MemberPurchasedColorResponseDTO from(ColorCode colorCode) {
        return new MemberPurchasedColorResponseDTO(
                colorCode.getId(),
                colorCode.getColorName(),
                colorCode.getRgb(),
                colorCode.getPrice()
        );
    }
}