package com.tdns.toks.core.domain.service;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.model.entity.User;
import com.tdns.toks.core.domain.repository.UserRepository;
import com.tdns.toks.core.domain.type.UserProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
