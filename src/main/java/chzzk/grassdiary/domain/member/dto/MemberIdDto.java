package chzzk.grassdiary.domain.member.dto;

import chzzk.grassdiary.domain.member.entity.Member;

public record MemberIdDto(
        Long memberId
) {
    public static MemberIdDto from(Member foundMember) {
        return new MemberIdDto(foundMember.getId());
    }
}
