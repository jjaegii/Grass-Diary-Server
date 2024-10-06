package chzzk.grassdiary.domain.member.dto;


import java.util.List;

public record MemberPurchasedColorsResponseDTO(
        List<MemberPurchasedColorResponseDTO> colors
) {
    public static MemberPurchasedColorsResponseDTO from(List<MemberPurchasedColorResponseDTO> colors) {
        return new MemberPurchasedColorsResponseDTO(colors);
    }
}