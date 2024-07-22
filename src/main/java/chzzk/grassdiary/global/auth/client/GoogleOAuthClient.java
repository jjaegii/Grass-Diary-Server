package chzzk.grassdiary.global.auth.client;

import chzzk.grassdiary.global.auth.config.GoogleOAuthProperties;
import chzzk.grassdiary.global.auth.service.dto.GoogleOAuthToken;
import chzzk.grassdiary.global.auth.service.dto.GoogleUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class GoogleOAuthClient implements OAuthClient {
    private static final String OAUTH_GRANT_TYPE = "authorization_code";
    private final GoogleOAuthProperties googleOAuthProperties;
    private final RestTemplate restTemplate;

    public GoogleOAuthClient(GoogleOAuthProperties googleOAuthProperties, RestTemplateBuilder restTemplateBuilder) {
        this.googleOAuthProperties = googleOAuthProperties;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public GoogleOAuthToken getGoogleAccessToken(String code) {
        HttpHeaders httpHeaders = createGoogleAccessRequestHeaders();

        MultiValueMap<String, String> httpBody = createBody(code);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpBody, httpHeaders);
        ResponseEntity<GoogleOAuthToken> googleOAuthTokenResponseEntity = restTemplate.postForEntity(
                googleOAuthProperties.getTokenUri(), request, GoogleOAuthToken.class);

        log.info("=======> 구글로부터 받은 토큰: {}", googleOAuthTokenResponseEntity.getBody());
        return googleOAuthTokenResponseEntity.getBody();
    }

    @Override
    public GoogleUserInfo getGoogleUserInfo(String accessToken) {
        HttpHeaders httpHeaders = createUserInfoRequestHeaders(accessToken);

        GoogleUserInfo body = restTemplate.exchange(
                        googleOAuthProperties.getUserInfoUri(),
                        HttpMethod.GET,
                        new HttpEntity<>(httpHeaders),
                        GoogleUserInfo.class)
                .getBody();

        log.info("=====> 구글로부터 받아온 사용자 정보: 이메일 - {},  닉네임 - {}, 사진 - {}",
                body.email(), body.nickname(), body.picture());

        return body;
    }

    private HttpHeaders createGoogleAccessRequestHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return httpHeaders;
    }

    private MultiValueMap<String, String> createBody(String code) {
        MultiValueMap<String, String> httpBody = new LinkedMultiValueMap<>();
        httpBody.add("code", code);
        httpBody.add("client_id", googleOAuthProperties.getClientId());
        httpBody.add("client_secret", googleOAuthProperties.getClientSecret());
        httpBody.add("redirect_uri", googleOAuthProperties.getRedirectUri());
        httpBody.add("grant_type", OAUTH_GRANT_TYPE);
        return httpBody;
    }

    private HttpHeaders createUserInfoRequestHeaders(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        return httpHeaders;
    }
}
