package com.tdns.toks.api.domain.user.service;

import com.tdns.toks.api.domain.user.model.dto.UserModel;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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

    @Async
    public CompletableFuture<Map<Long, UserModel>> asyncGetUsers(Set<Long> uids) {
        return CompletableFuture.completedFuture(getUsers(uids));
    }
}
