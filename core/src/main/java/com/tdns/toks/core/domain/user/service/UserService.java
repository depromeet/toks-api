package com.tdns.toks.core.domain.user.service;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.common.security.JwtTokenProvider;
import com.tdns.toks.core.domain.study.model.entity.StudyUser;
import com.tdns.toks.core.domain.study.repository.StudyUserRepository;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StudyUserRepository studyUserRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public Optional<User> getUserByStatus(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER));
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
        if (!jwtTokenProvider.verifyToken(requestRefreshToken)) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INVALID_REFRESH_TOKEN);
        }
        String email = jwtTokenProvider.getUid(requestRefreshToken);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));
        String userRefreshToken = user.getRefreshToken();
        if (!requestRefreshToken.equals(userRefreshToken)) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INVALID_REFRESH_TOKEN);
        }
        return jwtTokenProvider.renewAccessToken(user.getEmail());
    }

    public void deleteRefreshToken(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER));
        user.setRefreshToken("logout");
    }

    public List<StudyUser> getUserStudyIds(Long userId) {
        return studyUserRepository.findAllByUserId(userId);
    }

    private boolean isNicknameDuplicated(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
