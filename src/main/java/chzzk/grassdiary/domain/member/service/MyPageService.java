package chzzk.grassdiary.domain.member.service;

import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.domain.member.dto.MemberInfoDTO;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버 입니다. (id: " + id + ")"));
        return new MemberInfoDTO(member.getPicture(), member.getNickname(), member.getProfileIntro());
    }
}
