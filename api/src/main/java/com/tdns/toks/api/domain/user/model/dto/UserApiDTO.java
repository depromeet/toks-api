package com.tdns.toks.api.domain.user.model.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

public class UserApiDTO {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserUpdateNicknameRequest {
        @NotEmpty(message = "닉네임은 필수 항목입니다.")
        private String nickname;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserUpdateNicknameResponse {
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
