package chzzk.grassdiary.global.auth.controller;

import chzzk.grassdiary.global.auth.service.OAuthService;
import chzzk.grassdiary.global.auth.service.dto.JWTTokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;

    @Value("${client.login-success-redirect-uri}")
    private String loginSuccessUri;

    @GetMapping("/google")
    public void loginGoogle(HttpServletResponse response) throws IOException {
        String redirectUri = oAuthService.findRedirectUri();
        response.sendRedirect(redirectUri);
    }

    @GetMapping("/code/google")
    public void authorizeUser(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        JWTTokenResponse jwtToken = oAuthService.loginGoogle(code);
        String redirectUriWithJwt = generateRedirectUri(jwtToken.accessToken());
        log.info("jwt 토큰이 담긴 url = {}", redirectUriWithJwt);
        response.sendRedirect(redirectUriWithJwt);
    }

    private String generateRedirectUri(String accessToken) {
        return String.format("%s?accessToken=%s", loginSuccessUri, accessToken);
    }
}
