package com.tdns.toks.api.domain.auth.service;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.common.security.TokenService;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthV2Service {
    private final UserRepository userRepository;
    private final TokenService tokenService;

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

    public UserApiDTO.UserRenewAccessTokenResponse renewAccessToken(UserApiDTO.UserRenewAccessTokenRequest request) {
        var refreshToken = request.getRefreshToken();

        tokenService.verifyToken(refreshToken);

        var user = userRepository.findById(tokenService.getUserIdFromToken(refreshToken))
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));

        var userRefreshToken = user.getRefreshToken();
        if (!refreshToken.equals(userRefreshToken)) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INVALID_REFRESH_TOKEN);
        }

        var accessToken = tokenService.renewAccessToken(user.getId(), user.getEmail());

        return new UserApiDTO.UserRenewAccessTokenResponse(accessToken);
    }

    @Transactional
    public void deleteRefreshToken(long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER));
        user.setRefreshToken("logout");
    }
}
