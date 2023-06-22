package com.tdns.toks.api.domain.auth.service;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

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
}
