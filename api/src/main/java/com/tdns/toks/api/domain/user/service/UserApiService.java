package com.tdns.toks.api.domain.user.service;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserRenewAccessTokenRequest;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserRenewAccessTokenResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserInfoResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserUpdateNicknameRequest;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserApiService {
    private final UserService userService;

    public UserInfoResponse updateNickname(UserUpdateNicknameRequest userUpdateNicknameRequest) {
        var userDTO = UserDetailDTO.get();
        var user = userService.updateNickname(userDTO.getId(), userUpdateNicknameRequest.getNickname());
        return convertUserEntityToUserInfo(user);
    }

    public UserRenewAccessTokenResponse renewAccessToken(UserRenewAccessTokenRequest userRenewAccessTokenRequest) {
        String accessToken = userService.renewAccessToken(userRenewAccessTokenRequest.getRefreshToken());
        if (accessToken.equals("expired")) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INVALID_REFRESH_TOKEN);
        }
        return new UserRenewAccessTokenResponse(accessToken);
    }

    private UserInfoResponse convertUserEntityToUserInfo(User user) {
        return UserInfoResponse
                .builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .thumbnailImageUrl(user.getThumbnailImageUrl())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
