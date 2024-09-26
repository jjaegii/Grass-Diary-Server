package chzzk.grassdiary.domain.member.service;

import chzzk.grassdiary.domain.member.dto.MemberPurchasedColorResponseDTO;
import chzzk.grassdiary.domain.member.dto.MemberPurchasedColorsResponseDTO;
import chzzk.grassdiary.domain.member.entity.MemberPurchasedColorDAO;
import chzzk.grassdiary.global.auth.service.dto.GoogleUserInfo;
import chzzk.grassdiary.domain.color.entity.ColorCode;
import chzzk.grassdiary.domain.color.entity.ColorCodeDAO;
import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import java.util.ArrayList;
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
    private static final int DEFAULT_PRICE = 0;

    private final MemberDAO memberDAO;
    private final ColorCodeDAO colorCodeDAO;
    private final MemberPurchasedColorDAO memberPurchasedColorDAO;

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
                                .price(DEFAULT_PRICE)
                                .build()
                ));
    }

    public MemberPurchasedColorsResponseDTO getPurchasedColors(Long memberId) {
        if (!memberDAO.existsById(memberId)) {
            throw new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR);
        }

        List<MemberPurchasedColorResponseDTO> colors = new ArrayList<>(
                memberPurchasedColorDAO.findAllByMemberId(memberId).stream()
                        .map(MemberPurchasedColorResponseDTO::from)
                        .toList()
        );

        colors.add(getDefaultColorDTO());

        return MemberPurchasedColorsResponseDTO.from(colors);
    }

    private MemberPurchasedColorResponseDTO getDefaultColorDTO() {
        ColorCode defaultColorCode = colorCodeDAO.findByColorName(DEFAULT_COLOR_NAME)
                .orElseThrow(() -> new SystemException(ClientErrorCode.COLOR_CODE_NOT_FOUND_ERR));

        return MemberPurchasedColorResponseDTO.from(defaultColorCode);
    }
}
