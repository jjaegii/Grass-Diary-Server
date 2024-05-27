package chzzk.grassdiary.global.auth.client;

import chzzk.grassdiary.global.auth.service.dto.GoogleOAuthToken;
import chzzk.grassdiary.global.auth.service.dto.GoogleUserInfo;

public interface OAuthClient {
    GoogleOAuthToken getGoogleAccessToken(String code);

    GoogleUserInfo getGoogleUserInfo(String accessToken);
}
