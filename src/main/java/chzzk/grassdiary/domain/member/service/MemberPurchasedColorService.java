package chzzk.grassdiary.domain.member.service;

import chzzk.grassdiary.domain.color.entity.ColorCode;
import chzzk.grassdiary.domain.color.entity.ColorCodeDAO;
import chzzk.grassdiary.domain.member.dto.MemberPurchasedColorResponseDTO;
import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.domain.member.entity.MemberPurchasedColor;
import chzzk.grassdiary.domain.member.entity.MemberPurchasedColorDAO;
import chzzk.grassdiary.domain.reward.RewardHistory;
import chzzk.grassdiary.domain.reward.RewardHistoryDAO;
import chzzk.grassdiary.domain.reward.RewardType;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberPurchasedColorService {
    private final MemberDAO memberDAO;
    private final ColorCodeDAO colorCodeDAO;
    private final MemberPurchasedColorDAO memberPurchasedColorDAO;
    private final RewardHistoryDAO rewardHistoryDAO;

    @Transactional
    public MemberPurchasedColorResponseDTO purchaseColor(Long colorCodeId, Long logInmemberId) {
        Member member = getMemberById(logInmemberId);
        ColorCode colorCode = getColorCodeById(colorCodeId);
        validateAlreadyPurchased(colorCodeId, logInmemberId);
        validateEnoughRewardPoint(colorCode, member);
        member.deductRewardPoints(colorCode.getPrice());
        saveRewardHistory(member, colorCode);
        MemberPurchasedColor memberPurchasedColor = registerPurchase(member, colorCode);

        return MemberPurchasedColorResponseDTO.from(memberPurchasedColor);
    }

    private Member getMemberById(Long id) {
        return memberDAO.findById(id)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));
    }

    private ColorCode getColorCodeById(Long colorCodeId) {
        return colorCodeDAO.findById(colorCodeId)
                .orElseThrow(() -> new SystemException(ClientErrorCode.COLOR_CODE_NOT_FOUND_ERR));
    }

    private void validateAlreadyPurchased(Long colorCodeId, Long memberId) {
        boolean alreadyPurchased = memberPurchasedColorDAO.existsByColorCodeIdAndMemberId(colorCodeId,memberId);
        if (alreadyPurchased) {
            throw new SystemException(ClientErrorCode.COLOR_ALREADY_PURCHASED_ERR);
        }
    }

    private void validateEnoughRewardPoint(ColorCode colorCode, Member member) {
        if (member.getRewardPoint() < colorCode.getPrice()) {
            throw new SystemException(ClientErrorCode.INSUFFICIENT_REWARD_POINTS_ERR);
        }
    }

    private void saveRewardHistory(Member member, ColorCode colorCode) {
        RewardHistory rewardHistory = RewardHistory.builder()
                .member(member)
                .rewardType(RewardType.MINUS_SHOP_PURCHASE)
                .rewardPoint(-colorCode.getPrice())
                .build();
        rewardHistoryDAO.save(rewardHistory);
    }

    private MemberPurchasedColor registerPurchase(Member member, ColorCode colorCode) {
        MemberPurchasedColor memberPurchasedColor = MemberPurchasedColor.builder()
                .member(member)
                .colorCode(colorCode)
                .build();
        return memberPurchasedColorDAO.save(memberPurchasedColor);
    }
}
