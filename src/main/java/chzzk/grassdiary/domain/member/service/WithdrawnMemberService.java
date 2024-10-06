package chzzk.grassdiary.domain.member.service;

import chzzk.grassdiary.domain.comment.entity.Comment;
import chzzk.grassdiary.domain.comment.entity.CommentDAO;
import chzzk.grassdiary.domain.comment.service.CommentService;
import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.DiaryDAO;
import chzzk.grassdiary.domain.diary.service.DiaryService;
import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.domain.member.entity.MemberPurchasedColorDAO;
import chzzk.grassdiary.domain.member.entity.WithdrawnMember;
import chzzk.grassdiary.domain.member.entity.WithdrawnMemberDAO;
import chzzk.grassdiary.domain.reward.service.RewardService;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import chzzk.grassdiary.global.common.response.ServerErrorCode;
import chzzk.grassdiary.global.util.hash.EmailHasher;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WithdrawnMemberService {
    private final MemberDAO memberDAO;
    private final DiaryDAO diaryDAO;
    private final CommentDAO commentDAO;
    private final WithdrawnMemberDAO withdrawnMemberDAO;
    private final MemberPurchasedColorDAO memberPurchasedColorDAO;
    private final DiaryService diaryService;
    private final CommentService commentService;
    private final RewardService rewardService;
    private final EmailHasher emailHasher;

    @Transactional
    public void withdrawMember(Long logInMemberId) {
        Member member = findMember(logInMemberId);

        deleteDiaries(logInMemberId);

        deleteComments(logInMemberId);

        deleteRewards(logInMemberId);

        //구입한 잔디 색깔 이력 삭제
        deleteMemberPurchasedColor(logInMemberId);

        //email 해싱
        String hashedEmail = hashEmail(member.getEmail());

        // 해싱된 이메일을 WithdrawnMember에 저장
        saveWithdrawnMember(hashedEmail);

        // member의 값들 식별불가하게 변경
        member.withdrawMember();
        memberDAO.save(member);
    }

    @Transactional
    public void checkWithdrawnMember(String email) {
        String hashedEmail = hashEmail(email);
        if (withdrawnMemberDAO.existsByHashedEmail(hashedEmail)) {
            throw new SystemException(ClientErrorCode.ALREADY_WITHDRAWN_MEMBER_ERR);
        }
    }

    private Member findMember(Long memberId) {
        return memberDAO.findById(memberId)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));
    }

    private void deleteDiaries(long memberId) {
        List<Diary> diaries = diaryDAO.findAllByMemberId(memberId);

        for (Diary diary : diaries) {
            diaryService.delete(diary.getId(), memberId);
        }
    }

    private void deleteComments(long memberId) {
        List<Comment> comments = commentDAO.findAllByMemberId(memberId);

        for (Comment comment : comments) {
            commentService.delete(comment.getId(), memberId);
        }
    }

    private void deleteRewards(long memberId) {
        rewardService.deleteAllRewardHistory(memberId);
    }

    private void deleteMemberPurchasedColor(long memberId) {
        memberPurchasedColorDAO.deleteAllByMemberId(memberId);
    }

    // 이메일 해싱 메서드
    private String hashEmail(String email) {
        try {
            return emailHasher.hashEmail(email);  // 이메일 해싱
        } catch (Exception e) {
            throw new SystemException(ServerErrorCode.HASHING_FAILED, e);
        }
    }

    // WithdrawnMember 저장 메서드
    private void saveWithdrawnMember(String hashedEmail) {
        WithdrawnMember withdrawnMember = WithdrawnMember.builder()
                .hashedEmail(hashedEmail)
                .build();

        withdrawnMemberDAO.save(withdrawnMember);  // WithdrawnMember 엔티티 저장
    }
}
