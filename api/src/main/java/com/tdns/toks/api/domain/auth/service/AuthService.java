package com.tdns.toks.api.domain.auth.service;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.security.TokenService;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${not-set-nickname}")
    private String NOT_SET_NICKNAME;

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserApiDTO.UserInfoResponse getMyInfos(AuthUser authUser) {
        var user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));
        if (user.getNickname().equals(NOT_SET_NICKNAME)) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_SET_NICKNAME);
        }
        return new UserApiDTO.UserInfoResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getThumbnailImageUrl(),
                user.getProfileImageUrl()
        );
    }

    public UserApiDTO.UserRenewAccessTokenResponse renewAccessToken(
//            AuthUser authUser,
            UserApiDTO.UserRenewAccessTokenRequest request
    ) {
        var refreshToken = request.getRefreshToken();

        tokenService.verifyToken(refreshToken);

        var user = userRepository.findById(tokenService.getUserIdFromToken(refreshToken))
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));

        var userRefreshToken = user.getRefreshToken();
        if (!refreshToken.equals(userRefreshToken)) {
            throw new ApplicationErrorException(ApplicationErrorType.INVALID_REFRESH_TOKEN);
        }

        var accessToken = tokenService.renewAccessToken(user.getId(), user.getEmail());

        return new UserApiDTO.UserRenewAccessTokenResponse(accessToken);
    }

    @Transactional
    public void deleteRefreshToken(AuthUser authUser) {
        var user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER));
        user.setRefreshToken("logout");
    }
}
