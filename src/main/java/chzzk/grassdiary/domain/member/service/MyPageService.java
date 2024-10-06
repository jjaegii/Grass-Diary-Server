package chzzk.grassdiary.domain.member.service;

import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.domain.member.dto.MemberInfoDTO;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyPageService {
    private final MemberDAO memberDAO;

    @Transactional(readOnly = true)
    public MemberInfoDTO findProfileById(Long id) {
        Member member = memberDAO.findById(id)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));
        return new MemberInfoDTO(member.getPicture(), member.getNickname(), member.getProfileIntro(), member.getEmail());
    }
}
