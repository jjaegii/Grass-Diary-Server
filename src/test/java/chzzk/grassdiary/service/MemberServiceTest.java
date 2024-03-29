//package chzzk.grassdiary.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import chzzk.grassdiary.auth.service.dto.GoogleUserInfo;
//import chzzk.grassdiary.domain.member.Member;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class MemberServiceTest {
//
//    @Autowired
//    private MemberService memberService;
//
//    @Test
//    public void createMember() {
//        // given
//        GoogleUserInfo googleUserInfo = new GoogleUserInfo(
//                "1fkldkf23ss",
//                "test@gmail.com",
//                "aaa",
//                "fbkbkl2390dseggfgklsgk"
//        );
//
//        // when
//        Member member = memberService.createMemberIfNotExist(googleUserInfo);
//
//        // then
//        assertThat(member.getCurrentColorCode().getColorName()).isEqualTo("GREEN");
//        assertThat(member.getCurrentColorCode().getRgb()).isEqualTo("0,255,0");
//        assertThat(member.getGrassCount()).isEqualTo(0L);
//        assertThat(member.getRewardPoint()).isEqualTo(0);
//        assertThat(member.getProfileIntro()).isEqualTo("");
//        assertThat(member.getEmail()).isEqualTo("test@gmail.com");
//        assertThat(member.getNickname()).isEqualTo("aaa");
//        assertThat(member.getPicture()).isEqualTo("fbkbkl2390dseggfgklsgk");
//    }
//}