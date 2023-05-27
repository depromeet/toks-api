package com.tdns.toks.api.domain.user.service;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserInfoResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserRenewAccessTokenRequest;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserRenewAccessTokenResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserUpdateNicknameRequest;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserUpdateNicknameResponse;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserApiService {
    private final UserService userService;

    public UserInfoResponse getUserInfo() {
        var userDTO = UserDetailDTO.get();
        var user = userService.getUser(userDTO.getId());
        return convertUserEntityToUserInfo(user);
    }

    public UserUpdateNicknameResponse updateNickname(UserUpdateNicknameRequest userUpdateNicknameRequest) {
        var userDTO = UserDetailDTO.get();
        var updatedUserNickname = userService.updateNickname(userDTO.getId(), userUpdateNicknameRequest.getNickname());
        return new UserUpdateNicknameResponse(updatedUserNickname);
    }

    public UserRenewAccessTokenResponse renewAccessToken(UserRenewAccessTokenRequest userRenewAccessTokenRequest) {
        var accessToken = userService.renewAccessToken(userRenewAccessTokenRequest.getRefreshToken());
        return new UserRenewAccessTokenResponse(accessToken);
    }

    public void deleteRefreshToken() {
        var userDTO = UserDetailDTO.get();
        userService.deleteRefreshToken(userDTO.getId());
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
