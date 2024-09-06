package chzzk.grassdiary.domain.member.service;

import chzzk.grassdiary.domain.color.ColorCodeDAO;
import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import chzzk.grassdiary.global.auth.service.dto.GoogleUserInfo;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberDAO memberDAO;

    @Mock
    private ColorCodeDAO colorCodeDAO;

    FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();

    @Test
    @DisplayName("회원가입 되어 있지 않은 경우 신규 멤버로 데이터가 추가 되어야 한다.")
    void createMemberIfNotExist_whenMemberDoesNotExist() {
        // given
        GoogleUserInfo googleUserInfo = fixtureMonkey.giveMeOne(GoogleUserInfo.class);

        when(memberDAO.findByEmail(googleUserInfo.email())).thenReturn(Optional.empty());
        when(memberDAO.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Member createdMember = memberService.createMemberIfNotExist(googleUserInfo);

        // then
        then(createdMember).isNotNull();
        then(createdMember.getNickname()).isEqualTo(googleUserInfo.nickname());
        then(createdMember.getEmail()).isEqualTo(googleUserInfo.email());
        then(createdMember.getPicture()).isEqualTo(googleUserInfo.picture());
        
        verify(memberDAO).findByEmail(googleUserInfo.email());
        verify(memberDAO).save(any(Member.class));
    }

    @Test
    @DisplayName("회원가입이 이미 되어있는 경우 멤버 데이터를 추가하지 않는다.")
    void createMemberIfNotExist_whenMemberAlreadyExists() {
        // given
        GoogleUserInfo googleUserInfo = fixtureMonkey.giveMeOne(GoogleUserInfo.class);
        Member existingMember = fixtureMonkey.giveMeOne(Member.class);

        when(memberDAO.findByEmail(googleUserInfo.email())).thenReturn(Optional.of(existingMember));

        // when
        Member result = memberService.createMemberIfNotExist(googleUserInfo);

        // then
        then(result).isEqualTo(existingMember);
        verify(memberDAO).findByEmail(googleUserInfo.email());
        verify(memberDAO, never()).save(any(Member.class));
    }
}