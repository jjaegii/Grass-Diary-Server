package chzzk.grassdiary.domain.member.service;

import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.domain.member.dto.MemberIdDto;
import chzzk.grassdiary.domain.member.dto.MemberUpdateRequest;
import chzzk.grassdiary.domain.member.dto.MemberUpdatedResponse;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SettingService {
    private final MemberDAO memberDAO;

    public MemberUpdatedResponse updateMemberInfo(Long id, MemberUpdateRequest request) {
        Member foundMember = memberDAO.findById(id)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));

        foundMember.updateProfile(request.nickname(), request.profileIntro());

        memberDAO.save(foundMember);

        return new MemberUpdatedResponse(foundMember.getNickname(), foundMember.getProfileIntro());
    }

    public MemberIdDto findMemberInfo(Long id) {
        Member foundMember = memberDAO.findById(id)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));
        return MemberIdDto.from(foundMember);
    }
}
