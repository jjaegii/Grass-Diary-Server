//package chzzk.grassdiary.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import chzzk.grassdiary.domain.color.ColorCode;
//import chzzk.grassdiary.domain.color.ColorCodeRepository;
//import chzzk.grassdiary.domain.color.ConditionLevel;
//import chzzk.grassdiary.domain.diary.Diary;
//import chzzk.grassdiary.domain.diary.DiaryRepository;
//import chzzk.grassdiary.domain.diary.tag.DiaryTagRepository;
//import chzzk.grassdiary.domain.diary.tag.MemberTagsRepository;
//import chzzk.grassdiary.domain.diary.tag.TagListRepository;
//import chzzk.grassdiary.domain.member.Member;
//import chzzk.grassdiary.domain.member.MemberRepository;
//import chzzk.grassdiary.service.diary.DiaryService;
//import chzzk.grassdiary.web.dto.diary.CountAndMonthGrassDTO;
//import chzzk.grassdiary.web.dto.diary.DiaryDTO;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class DiaryServiceTest {
//    protected List<Member> members;
//    protected List<Diary> diaries;
//    protected List<ColorCode> colorCodes;
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    DiaryRepository diaryRepository;
//    @Autowired
//    MemberTagsRepository memberTagsRepository;
//    @Autowired
//    TagListRepository tagListRepository;
//    @Autowired
//    DiaryTagRepository diaryTagRepository;
//    @Autowired
//    ColorCodeRepository colorCodeRepository;
//    @Autowired
//    DiaryService diaryService;
//
//    @BeforeEach
//    void beforeEach() {
//        // 컬러코드 값 넣기
//        colorCodes = new ArrayList<>();
//        colorCodes.add(ColorCode.builder()
//                .colorName("GREEN")
//                .rgb("000,000,000")
//                .build());
//        colorCodes = colorCodeRepository.saveAll(colorCodes);
//
//        // 멤버 3명 만들기
//        members = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            members.add(Member.builder()
//                    .nickname("testMember" + i)
//                    .email("member" + i + "@test.com")
//                    .currentColorCode(colorCodes.get(0))
//                    .build());
//        }
//        members = memberRepository.saveAll(members);
//
//        // 일기 추가
//        diaries = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            diaries.add(Diary.builder()
//                    .member(members.get(i % 3))
//                    .content("testContent" + i)
//                    .hasTag(false)
//                    .conditionLevel(ConditionLevel.LEVEL_5)
//                    .build());
//        }
//        diaries = diaryRepository.saveAll(diaries);
//    }
//
//    @Test
//    @DisplayName("유저의 날짜별 일기 검색(잔디 클릭시 실행)")
//    void findByDate() {
//        // Given
//        Long memberId = members.get(0).getId();
//
//        // When
//        DiaryDTO diaryDTO = diaryService.findByDate(memberId, LocalDate.now().format(DateTimeFormatter.ISO_DATE));
//
//        // Then
//        assertThat(diaryDTO).isNotNull();
//        assertThat(diaryDTO.content()).isEqualTo("testContent0");
//        assertThat(diaryDTO.createdDate()).isEqualTo(
//                LocalDate.now().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")));
//
//    }
//
//    @Test
//    @DisplayName("유저별 모든 일기 개수, 이번 달 잔디 정보 검색")
//    void countAllAndMonthGrass() {
//        // Given
//        Long memberId = members.get(0).getId();
//
//        // When
//        CountAndMonthGrassDTO countAndMonthGrass = diaryService.countAllAndMonthGrass(memberId);
//
//        // Then
//        assertThat(countAndMonthGrass).isNotNull();
//        assertThat(countAndMonthGrass.count()).isEqualTo(1);
//        assertThat(countAndMonthGrass.grassInfoDTO().getGrassList().size()).isEqualTo(1);
//    }
//}