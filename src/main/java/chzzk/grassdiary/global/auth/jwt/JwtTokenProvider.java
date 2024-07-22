package chzzk.grassdiary.global.auth.jwt;

import chzzk.grassdiary.global.auth.service.dto.AuthMemberPayload;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ClientErrorCode;
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
            throw new SystemException(ClientErrorCode.AUTH_MISSING_ID_IN_ACCESS_TOKEN);
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
            throw new SystemException(ClientErrorCode.AUTH_INVALID_ACCESS_TOKEN, exception);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new SystemException(ClientErrorCode.AUTH_EXPIRED_ACCESS_TOKEN, expiredJwtException);
        } catch (SignatureException signatureException) {
            throw new SystemException(ClientErrorCode.AUTH_INVALID_SIGNATURE);
        } catch (RuntimeException exception) { // 기타 오류 발생
            throw new SystemException(ClientErrorCode.AUTH_JWT_ERROR);
        }
    }
}
