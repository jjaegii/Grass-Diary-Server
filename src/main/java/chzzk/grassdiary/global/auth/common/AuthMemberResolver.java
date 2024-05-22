package chzzk.grassdiary.global.auth.common;

import chzzk.grassdiary.global.auth.exception.AuthenticationException;
import chzzk.grassdiary.global.auth.jwt.JwtTokenProvider;
import chzzk.grassdiary.global.auth.service.dto.AuthMemberPayload;
import chzzk.grassdiary.domain.member.entity.Member;
import chzzk.grassdiary.domain.member.entity.MemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthMemberResolver implements HandlerMethodArgumentResolver {
    private final static String SPLIT_DELIMITER = " ";
    private static final int TOKEN_INDEX = 1;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDAO memberDAO;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = authorization.split(SPLIT_DELIMITER)[TOKEN_INDEX];
        Long memberId = jwtTokenProvider.extractIdFromAccessToken(jwtToken);

        Member foundMember = memberDAO.findById(memberId)
                .orElseThrow(() -> new AuthenticationException("로그인이 필요한 사용자입니다."));

        return new AuthMemberPayload(foundMember.getId());
    }
}
