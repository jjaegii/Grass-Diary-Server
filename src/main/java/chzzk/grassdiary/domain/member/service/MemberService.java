package chzzk.grassdiary.domain.member.service;

import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.DiaryDAO;
import chzzk.grassdiary.global.auth.service.dto.GoogleUserInfo;
import chzzk.grassdiary.domain.color.ColorCode;
import chzzk.grassdiary.domain.color.ColorCodeDAO;
import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private static final String DEFAULT_COLOR_NAME = "GREEN";
    private static final String DEFAULT_RGB = "0,255,0";
    private final MemberDAO memberDAO;
    private final ColorCodeDAO colorCodeDAO;

    public Member createMemberIfNotExist(GoogleUserInfo googleUserInfo) {
        Optional<Member> foundMember = memberDAO.findByEmail(googleUserInfo.email());

        if (foundMember.isPresent()) {
            return foundMember.get();
        }

        Member member = Member.of(
                googleUserInfo.nickname(),
                googleUserInfo.email(),
                googleUserInfo.picture(),
                getDefaultColorCode());

        return memberDAO.save(member);
    }

    private ColorCode getDefaultColorCode() {
        return colorCodeDAO.findByColorName(DEFAULT_COLOR_NAME)
                .orElseGet(() -> colorCodeDAO.save(
                        ColorCode.builder()
                                .colorName(DEFAULT_COLOR_NAME)
                                .rgb(DEFAULT_RGB)
                                .build()
                ));
    }
}
