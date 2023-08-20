package com.tdns.toks.api.domain.user.service;

import com.tdns.toks.api.domain.user.model.UserModel;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Map<Long, UserModel> getUsers(Set<Long> uids) {
        return userRepository.findAllById(uids)
                .stream()
                .map(UserModel::from)
                .collect(Collectors.toMap(UserModel::getId, Function.identity()));
    }

    @Transactional
    public String updateNickname(Long id, String nickname) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));
        if (isNicknameDuplicated(nickname)) {
            throw new ApplicationErrorException(ApplicationErrorType.DUPLICATED_NICKNAME);
        }
        user.updateNickname(nickname);
        return user.getNickname();
    }

    private boolean isNicknameDuplicated(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public Long countAllUsers() {
        return userRepository.count();
    }

    public Long countNewUsers() {
        var endAt = LocalDateTime.now();
        var startAt = endAt.minusDays(1);
        return userRepository.countByCreatedAtBetween(startAt, endAt);
    }

    public UserModel getUserModel(Long uid) {
        return userRepository.findById(uid)
                .map(UserModel::from)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER));
    }

    @Async
    public CompletableFuture<UserModel> asyncGetUserModel(Long uid) {
        return CompletableFuture.completedFuture(getUserModel(uid));
    }
}
