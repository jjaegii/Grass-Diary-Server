package chzzk.grassdiary.global.auth.jwt;

import chzzk.grassdiary.global.auth.exception.AuthenticationException;
import chzzk.grassdiary.global.auth.exception.JwtException;
import chzzk.grassdiary.global.auth.service.dto.AuthMemberPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private static final String EXPIRED_ACCESS_TOKEN_MESSAGE = "EXPIRED_ACCESS_TOKEN";
    private static final String MEMBER_ID = "id";
    private static final String ALGORITHM = "HmacSHA256";

    @Value("${jwt.access.secret-key}")
    private String jwtAccessTokenSecretKey;

    @Value("${jwt.access.expiration}")
    private long jwtAccessTokenExpiration;

    public String generateAccessToken(AuthMemberPayload payload) {
        long currentDateTime = new Date().getTime();
        Date expiryDate = new Date(currentDateTime + jwtAccessTokenExpiration);
        SecretKeySpec secretKey = new SecretKeySpec(
                jwtAccessTokenSecretKey.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .claim(MEMBER_ID, payload.id())
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Long extractIdFromAccessToken(String token) {
        validateAccessToken(token);
        Jws<Claims> claimsJws = getAccessTokenParser().parseClaimsJws(token);
        Long extractedId = claimsJws.getBody().get(MEMBER_ID, Long.class);

        if (extractedId == null) {
            String logMessage = "인증 실패(JWT 액세스 토큰 Payload id 누락) - 토큰 : " + token;
            throw new AuthenticationException(logMessage);
        }
        return extractedId;
    }

    private JwtParser getAccessTokenParser() {
        return Jwts.parserBuilder()
                .setSigningKey(new SecretKeySpec(
                        jwtAccessTokenSecretKey.getBytes(StandardCharsets.UTF_8),
                        ALGORITHM))
                .build();
    }

    private void validateAccessToken(String token) {
        try {
            Claims claims = getAccessTokenParser().parseClaimsJws(token).getBody();
        } catch (MalformedJwtException | UnsupportedJwtException exception) {
            String logMessage = "인증 실패(잘못된 액세스 토큰인 경우): " + token;
            throw new AuthenticationException(logMessage);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new AuthenticationException("인증 실패(토큰 만료인 경우): " + EXPIRED_ACCESS_TOKEN_MESSAGE);
        } catch (SignatureException signatureException) {
            String logMessage = "인증 실패(잘못된 시그니처인 경우): " + token;
            throw new AuthenticationException(logMessage);
        } catch (JwtException jwtException) {
            String logMessage = "인증 실패(기타 오류 발생): " + token;
            throw new AuthenticationException(logMessage);
        }
    }
}
