//package chzzk.grassdiary.service.diary;
//
//import chzzk.grassdiary.domain.member.entity.color.ColorCode;
//import chzzk.grassdiary.domain.member.entity.color.ColorCodeRepository;
//import chzzk.grassdiary.domain.member.entity.color.ConditionLevel;
//import chzzk.grassdiary.domain.diary.entity.dao.DiaryRepository;
//import chzzk.grassdiary.domain.member.entity.Member;
//import chzzk.grassdiary.domain.member.entity.MemberRepository;
//import chzzk.grassdiary.web.dto.diary.DiarySaveDTO;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class DiaryServiceTest {
//
//    @Autowired
//    private DiaryService diaryService;
//    @Autowired
//    private DiaryRepository diaryRepository;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private ColorCodeRepository colorCodeRepository;
//
//    @AfterEach
//    public void clear() {
//        diaryRepository.deleteAll();
//        memberRepository.deleteAll();
//        colorCodeRepository.deleteAll();
//    }
//
//    @Test
//    public void 글_생성() {
//        String expectedContent = "diary content";
//        String expectedNickname = "testNickName";
//
//        ColorCode colorCode = ColorCode.builder()
//                .colorName("GREEN")
//                .rgb("000,000,000")
//                .build();
//        Member member = Member.builder()
//                .nickname(expectedNickname)
//                .email("testEmail@test.com")
//                .currentColorCode(colorCode)
//                .build();
//        DiarySaveDTO.Request requestDto = DiarySaveDTO.Request.builder()
//                .member(member)
//                .content(expectedContent)
//                .isPrivate(true)
//                .hasImage(false)
//                .conditionLevel(ConditionLevel.LEVEL_10)
//                .build();
//
//        colorCodeRepository.save(colorCode);
//        memberRepository.save(member);
//        diaryService.save(1L, requestDto);
//
//        System.out.println("###################################");
//        System.out.println("###################################");
//        System.out.println(diaryRepository.count());
//        System.out.println("###################################");
//        System.out.println("###################################");
//
//        // 글 작성자 확인
//        Assertions.assertThat(diaryRepository.findById(1L).get().getMember().getNickname()).isEqualTo(expectedNickname);
//        // 글 내용 확인
//        Assertions.assertThat(diaryRepository.findById(1L).get().getContent()).isEqualTo(expectedContent);
//    }

//    @Test
//    public void 글_수정() {
//        String expectedContent = "update diary content";
//
//        ColorCode colorCode = ColorCode.builder()
//                .colorName("GREEN")
//                .rgb("000,000,000")
//                .build();
//        Member member = Member.builder()
//                .nickname("testNickName")
//                .email("testEmail@test.com")
//                .currentColorCode(colorCode)
//                .build();
//        DiaryDto.Request requestDto = DiaryDto.Request.builder()
//                .member(member)
//                .content("diary content")
//                .isPrivate(true)
//                .hasImage(false)
//                .conditionLevel(ConditionLevel.LEVEL_10)
//                .build();
//
//        colorCodeRepository.save(colorCode);
//        memberRepository.save(member);
//        diaryService.save(requestDto);
//
//        System.out.println("###################################");
//        System.out.println("###################################");
//        System.out.println(diaryRepository.count());
//        System.out.println("###################################");
//        System.out.println("###################################");
//
//        DiaryDto.Request updateRequestDto = DiaryDto.Request.builder()
//                .member(member)
//                .content(expectedContent)
//                .isPrivate(true)
//                .hasImage(false)
//                .conditionLevel(ConditionLevel.LEVEL_9)
//                .build();
//
//        diaryService.update(1L, updateRequestDto);
//
//        Assertions.assertThat(diaryRepository.findById(1L).get().getMember().getNickname()).isEqualTo("testNickName");
//
//        Assertions.assertThat(diaryRepository.findById(1L).get().getContent()).isEqualTo(expectedContent);
//    }
//
//    @Test
//    public void 글_삭제() {
//        ColorCode colorCode = ColorCode.builder()
//                .colorName("GREEN")
//                .rgb("000,000,000")
//                .build();
//        Member member = Member.builder()
//                .nickname("testNickName")
//                .email("testEmail@test.com")
//                .currentColorCode(colorCode)
//                .build();
//        DiaryDto.Request requestDto = DiaryDto.Request.builder()
//                .member(member)
//                .content("diary content")
//                .isPrivate(true)
//                .hasImage(false)
//                .conditionLevel(ConditionLevel.LEVEL_10)
//                .build();
//
//        Long id = 1L;
//
//        colorCodeRepository.save(colorCode);
//        memberRepository.save(member);
//        diaryService.save(requestDto);
//
//        System.out.println("###################################");
//        System.out.println("###################################");
//        System.out.println(diaryRepository.count());
//        System.out.println("###################################");
//        System.out.println("###################################");
//
//        diaryService.delete(id);
//
//        Assertions.assertThatThrownBy(() -> diaryService.findById(id))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("해당 일기가 존재하지 않습니다. id = " + id);
//    }
//}
