package chzzk.grassdiary.global.auth.jwt;

import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtTokenExtractor {
    private static final String PREFIX_BEARER = "Bearer ";
    private static final String ACCESS_TOKEN_HEADER = HttpHeaders.AUTHORIZATION;

    public String extractAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);

        if (StringUtils.hasText(accessToken) && accessToken.startsWith(PREFIX_BEARER)) {
            return accessToken.substring(PREFIX_BEARER.length());
        }
        throw new SystemException(ClientErrorCode.AUTH_TOKEN_EXTRACTION_FAILED);
    }
}
