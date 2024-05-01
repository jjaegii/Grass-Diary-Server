//package chzzk.grassdiary.domain.auth.jwt;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatCode;
//
//import chzzk.grassdiary.auth.jwt.JwtTokenProvider;
//import chzzk.grassdiary.auth.service.dto.AuthMemberPayload;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class JwtTokenProviderTest {
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Test
//    public void JWT_액세스_토큰_생성() {
//        // given
//        AuthMemberPayload payload = AuthMemberPayload.from(1L);
//
//        // when
//        String accessToken = jwtTokenProvider.generateAccessToken(payload);
//
//        // then
//        System.out.println("accessToken = " + accessToken);
//        assertThat(accessToken).isNotNull();
//    }
//
//    @Test
//    public void JWT_액세스_토큰_검증() {
//        // given
//        AuthMemberPayload payload = AuthMemberPayload.from(1L);
//        String accessToken = jwtTokenProvider.generateAccessToken(payload);
//
//        // when
//        // then
//        assertThatCode(() -> jwtTokenProvider.extractIdFromAccessToken(accessToken))
//                .doesNotThrowAnyException();
//    }
//}