package com.tdns.toks.api.domain.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tdns.toks.core.domain.user.type.UserProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

public class UserApiDTO {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(name="QRRequest", description="QR인증 요청 모델")
    public static class UserLoginRequest{
        @NotEmpty(message = "email은 필수 항목 입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "email", description = "xxxx@xxxx.xxx")
        private String email;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "provider", description = "ITSELF | KAKAO | NAVER etc...")
        private UserProvider provider;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "providerId", description = "써드파티의 ID")
        private String providerId;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(name="nicknameRequest", description="로그인 후 사용자 닉네임 요청 모델")
    public static class UserUpdateNicknameRequest {
        @NotEmpty(message = "닉네임은 필수 항목입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "nickname", description = "사용자 닉네임")
        private String nickname;
    }
}
