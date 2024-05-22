package chzzk.grassdiary.global.auth.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserInfo(
        String id,

        @JsonProperty("email")
        String email,

        @JsonProperty("given_name")
        String nickname,

        @JsonProperty("picture")
        String picture
) {
}
