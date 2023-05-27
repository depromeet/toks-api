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
    @Schema(name = "UserInfoResponse", description = "사용자 정보 응답 모델")
    public static class UserInfoResponse {
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "id", description = "사용자 email")
        private String email;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "nickname", description = "사용자 nickname")
        private String nickname;

        @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "thumbnailImageUrl", description = "작은 이미지 URL")
        private String thumbnailImageUrl;

        @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "profileImageUrl", description = "큰 이미지 URL")
        private String profileImageUrl;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "UserRenewAccessTokenRequest", description = "accessToken 갱신 요청 모델")
    public static class UserRenewAccessTokenRequest {
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "refreshToken", description = "리프래시 토큰")
        private String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "UserRenewAccessTokenResponse", description = "갱신된 AccessToken 응답 모델")
    public static class UserRenewAccessTokenResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "accessToken", description = "갱신된 엑세스 토큰")
        private String accessToken;
    }
}
