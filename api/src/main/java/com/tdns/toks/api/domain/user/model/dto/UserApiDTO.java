package com.tdns.toks.api.domain.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class UserApiDTO {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "NicknameRequest", description = "로그인 후 사용자 닉네임 요청 모델")
    public static class UserUpdateNicknameRequest {
        @NotEmpty(message = "닉네임은 필수 항목입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "nickname", description = "사용자 닉네임")
        private String nickname;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "NicknameResponse", description = "변경된 닉네임 반환 모델")
    public static class UserUpdateNicknameResponse {
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "nickname", description = "사용자 nickname")
        private String nickname;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfoResponse {
        private String email;

        private String nickname;

        private String thumbnailImageUrl;

        private String profileImageUrl;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRenewAccessTokenRequest {
        private String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRenewAccessTokenResponse {
        private String accessToken;
    }
}
