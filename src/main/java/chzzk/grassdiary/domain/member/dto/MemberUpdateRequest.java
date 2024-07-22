package chzzk.grassdiary.domain.member.dto;

public record MemberUpdateRequest(
        String nickname,
        String profileIntro
) {
}
