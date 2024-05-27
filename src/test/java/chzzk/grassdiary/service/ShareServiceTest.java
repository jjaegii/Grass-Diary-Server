//package chzzk.grassdiary.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import chzzk.grassdiary.domain.diary.entity.Diary;
//import chzzk.grassdiary.domain.diary.entity.dao.DiaryRepository;
//import chzzk.grassdiary.domain.member.entity.Member;
//import chzzk.grassdiary.domain.member.entity.MemberRepository;
//import chzzk.grassdiary.domain.diary.dto.browse.AllLatestDiariesDto;
//import chzzk.grassdiary.domain.diary.dto.browse.LatestDiaryDto;
//import chzzk.grassdiary.domain.diary.dto.browse.Top10DiariesDto;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class ShareServiceTest {
//    @Autowired
//    private ShareService shareService;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private DiaryRepository diaryRepository;
//
//    @BeforeEach
//    public void setUp() {
//        Member member = Member.builder()
//                .id(1L)
//                .email("test@gmail.com")
//                .nickname("xxx")
//                .build();
//
//        List<Diary> diaries = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            Diary diary = Diary.builder()
//                    .content("오늘은 맛있는 음식을 먹었다.")
//                    .isPrivate(false)
//                    .member(member)
//                    .build();
//            diaries.add(diary);
//        }
//
//        memberRepository.save(member);
//        diaryRepository.saveAll(diaries);
//    }
//
//    @Test
//    public void findTop10Diaries_ReturnsTop10Diaries() {
//        // when
//        List<Top10DiariesDto> top10Diaries = shareService.findTop10DiariesThisWeek();
//
//        // then
//        assertThat(top10Diaries.size()).isEqualTo(10);
//        for (Top10DiariesDto top10Diary : top10Diaries) {
//            System.out.println("top10Diary.diaryId() = " + top10Diary.diaryId());
//            System.out.println("top10Diary.diaryContent() = " + top10Diary.diaryContent());
//            System.out.println("top10Diary.nickname() = " + top10Diary.nickname());
//            System.out.println("top10Diary.diaryLikeCount() = " + top10Diary.diaryLikeCount());
//        }
//    }
//
//    @Test
//    public void findLatestDiariesTest() {
//        // when
//        AllLatestDiariesDto latestDiaries = shareService.findLatestDiariesAfterCursor(100L, 50);
//
//        // then
//        for (LatestDiaryDto latestDiary : latestDiaries.diaries()) {
//            System.out.println("latestDiary.content() = " + latestDiary.content());
//            System.out.println("latestDiary.nickname() = " + latestDiary.nickname());
//            System.out.println("latestDiary.diaryId() = " + latestDiary.diaryId());
//        }
//        assertThat(latestDiaries.diaries().size()).isEqualTo(50);
//        assertThat(latestDiaries.diaries().size()).isNotEqualTo(51);
//    }
//}