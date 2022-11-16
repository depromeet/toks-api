package com.tdns.toks.api.domain.user.service;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserInfoResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserUpdateNicknameRequest;
import com.tdns.toks.core.common.service.UserDetailService;
import com.tdns.toks.core.common.type.JwtToken;
import com.tdns.toks.core.common.security.JwtTokenProvider;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.service.UserService;
import com.tdns.toks.core.domain.user.type.UserRole;
import com.tdns.toks.core.domain.user.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserLoginRequest;

@Service
@RequiredArgsConstructor
public class UserApiService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtToken login(UserLoginRequest userLoginRequest) {
        User user = userService.getUser(userLoginRequest.getProvider(), userLoginRequest.getProviderId());
        if (user == null) {
            user = userService.createUser(convertToUserEntity(userLoginRequest));
        }
        return (jwtTokenProvider.generateToken(user.getEmail()));
    }

    public UserInfoResponse updateNickname(UserUpdateNicknameRequest userUpdateNicknameRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.updateNickname(email, userUpdateNicknameRequest.getNickname());
        return convertUserEntityToUserInfo(user);
    }

    private User convertToUserEntity(UserLoginRequest userLoginRequest){
        return User
                .builder()
                .email(userLoginRequest.getEmail())
                .nickname("random")
                .provider(userLoginRequest.getProvider())
                .providerId(userLoginRequest.getProviderId())
                .build();
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
