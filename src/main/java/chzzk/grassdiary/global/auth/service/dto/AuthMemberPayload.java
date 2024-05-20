package chzzk.grassdiary.global.auth.service.dto;

import chzzk.grassdiary.domain.member.entity.Member;

public record AuthMemberPayload(Long id) {
    public static AuthMemberPayload from(Member member) {
        return new AuthMemberPayload(member.getId());
    }

    public static AuthMemberPayload from(Long memberId) {
        return new AuthMemberPayload(memberId);
    }
}
