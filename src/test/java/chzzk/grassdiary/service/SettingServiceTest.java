//package chzzk.grassdiary.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import chzzk.grassdiary.domain.member.entity.Member;
//import chzzk.grassdiary.domain.member.entity.MemberRepository;
//import chzzk.grassdiary.domain.member.dto.MemberUpdateRequest;
//import chzzk.grassdiary.domain.member.dto.MemberUpdatedResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class SettingServiceTest {
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private SettingService settingService;
//
//    @Test
//    public void updateMemberInfo() {
//        // given
//        Member member = Member.builder()
//                .id(1L)
//                .nickname("xxx")
//                .profileIntro("Hello!!")
//                .build();
//
//        memberRepository.save(member);
//
//        String updatedNickname = "ooo";
//        String updatedProfile = "Bye";
//        MemberUpdateRequest request = new MemberUpdateRequest(
//                updatedNickname,
//                updatedProfile
//        );
//
//        // when
//        MemberUpdatedResponse response = settingService.updateMemberInfo(1L, request);
//
//        // then
//        assertThat(response.nickname()).isEqualTo("ooo");
//        assertThat(response.profileIntro()).isEqualTo("Bye");
//
//        System.out.println("response.nickname() = " + response.nickname());
//        System.out.println("response.profileIntro() = " + response.profileIntro());
//    }
//}