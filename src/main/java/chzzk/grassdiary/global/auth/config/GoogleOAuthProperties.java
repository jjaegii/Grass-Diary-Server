package chzzk.grassdiary.global.auth.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class GoogleOAuthProperties {

    @Value("${oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.client.registration.google.authorization-grant-type}")
    private String responseType;

    @Value("${oauth2.client.registration.google.scope}")
    private String scope;

    @Value("${oauth2.client.provider.google.authorization-uri}")
    private String authorizationUri;

    @Value("${oauth2.client.provider.google.token-uri}")
    private String tokenUri;

    @Value("${oauth2.client.provider.google.user-info-uri}")
    private String userInfoUri;
}
