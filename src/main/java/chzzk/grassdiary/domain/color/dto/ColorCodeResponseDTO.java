package chzzk.grassdiary.domain.color.dto;

import chzzk.grassdiary.domain.color.entity.ColorCode;

public record ColorCodeResponseDTO(
        Long id,
        String colorName,
        String rgb,
        int price
) {
    public static ColorCodeResponseDTO from(ColorCode colorCode) {
        return new ColorCodeResponseDTO(
                colorCode.getId(),
                colorCode.getColorName(),
                colorCode.getRgb(),
                colorCode.getPrice()
        );
    }
}
