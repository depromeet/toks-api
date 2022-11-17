package com.tdns.toks.core.domain.user.service;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import com.tdns.toks.core.domain.user.type.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.Optional;

import static java.util.Optional.empty;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> getUserByStatus(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUser(UserProvider provider, String providerId) {
        return userRepository.findByProviderAndProviderId(provider, providerId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateNickname(Long id, String nickname) {
        User user = userRepository.findById(id).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));
        if (isNicknameDuplicated(nickname)) {
            throw new SilentApplicationErrorException(ApplicationErrorType.DUPLICATED_NICKNAME);
        }
        user.updateNickname(nickname);
        return user;
    }

    public boolean isNicknameDuplicated(String nickname) {
        Optional<User> usedNickname = userRepository.findByNickname(nickname);
        return usedNickname.isPresent();
    }
}
