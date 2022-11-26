package com.tdns.toks.core.domain.user.service;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.common.security.JwtTokenProvider;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import com.tdns.toks.core.domain.user.type.UserProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public Optional<User> getUserByStatus(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public String updateNickname(Long id, String nickname) {
        User user = userRepository.findById(id).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));
        if (isNicknameDuplicated(nickname)) {
            throw new SilentApplicationErrorException(ApplicationErrorType.DUPLICATED_NICKNAME);
        }
        user.updateNickname(nickname);
        return user.getNickname();
    }

    public String renewAccessToken(String requestRefreshToken) {
        String email = jwtTokenProvider.getUid(requestRefreshToken);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));
        String userRefreshToken = user.getRefreshToken();
        if (jwtTokenProvider.verifyToken(requestRefreshToken) && requestRefreshToken.equals(userRefreshToken)) {
            return jwtTokenProvider.renewAccessToken(user.getEmail());
        }
        user.setRefreshToken("none");
        return "refreshToken expired";
    }

    private boolean isNicknameDuplicated(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
