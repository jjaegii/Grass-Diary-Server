package chzzk.grassdiary.domain.diary.service;

import chzzk.grassdiary.domain.diary.dto.CountAndMonthGrassDTO;
import chzzk.grassdiary.domain.diary.entity.Diary;
import chzzk.grassdiary.domain.diary.entity.DiaryDAO;
import chzzk.grassdiary.domain.member.dto.GrassInfoDTO;
import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.domain.color.entity.ColorCode;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GrassCountService {
    private final MemberDAO memberDAO;
    private final DiaryDAO diaryDAO;

    @Transactional(readOnly = true)
    public GrassInfoDTO findGrassHistoryById(Long memberId) {
        List<Diary> diaryHistory = diaryDAO.findAllByMemberIdOrderByCreatedAt(memberId);
        Member member = memberDAO.findById(memberId)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));
        ColorCode colorCode = member.getCurrentColorCode();
        return new GrassInfoDTO(diaryHistory, colorCode.getRgb());
    }

    /**
     * 유저의 총 잔디 개수(count) 유저의 이번 달 잔디 정보(grassInfoDTO<grassList>, colorRGB>)
     */
    @Transactional(readOnly = true)
    public CountAndMonthGrassDTO countAllAndMonthGrass(Long memberId) {
        List<Diary> allByMemberId = diaryDAO.findAllByMemberId(memberId);

        LocalDate startOfMonth = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate today = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth());
        LocalDateTime startOfDay = startOfMonth.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(LocalTime.MAX);

        Member member = memberDAO.findById(memberId)
                .orElseThrow(() -> new SystemException(ClientErrorCode.MEMBER_NOT_FOUND_ERR));

        List<Diary> thisMonthHistory = diaryDAO.findByMemberIdAndCreatedAtBetween(memberId, startOfDay,
                endOfToday);
        ColorCode colorCode = member.getCurrentColorCode();

        return new CountAndMonthGrassDTO(
                allByMemberId.size(),
                new GrassInfoDTO(thisMonthHistory, colorCode.getRgb()),
                thisMonthHistory.size()
        );
    }
}
