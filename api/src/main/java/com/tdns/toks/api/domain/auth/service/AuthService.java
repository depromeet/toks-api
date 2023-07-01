package com.tdns.toks.api.domain.auth.service;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.common.security.JwtTokenProvider;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserApiDTO.UserInfoResponse getMyInfos(AuthUser authUser) {
        var user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));

        return new UserApiDTO.UserInfoResponse(
                user.getEmail(),
                user.getNickname(),
                user.getThumbnailImageUrl(),
                user.getProfileImageUrl()
        );
    }

    public UserApiDTO.UserRenewAccessTokenResponse renewAccessToken(
            AuthUser authUser,
            UserApiDTO.UserRenewAccessTokenRequest request
    ) {
        var requestRefreshToken = request.getRefreshToken();

        if (!jwtTokenProvider.verifyToken(requestRefreshToken)) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INVALID_REFRESH_TOKEN);
        }

        var email = jwtTokenProvider.getUserEmail(requestRefreshToken);

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));

        var userRefreshToken = user.getRefreshToken();
        if (!requestRefreshToken.equals(userRefreshToken)) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INVALID_REFRESH_TOKEN);
        }

        var accessToken = jwtTokenProvider.renewAccessToken(user.getId(), user.getEmail());

        return new UserApiDTO.UserRenewAccessTokenResponse(accessToken);
    }

    public void deleteRefreshToken(AuthUser authUser) {
        var user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER));
        user.setRefreshToken("logout");
    }
}
