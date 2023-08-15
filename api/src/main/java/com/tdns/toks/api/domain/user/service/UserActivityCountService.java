package com.tdns.toks.api.domain.user.service;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.user.entity.UserActivityCount;
import com.tdns.toks.core.domain.user.repository.UserActivityCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserActivityCountService {
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;
    private final UserActivityCountRepository userActivityCountRepository;

    public UserActivityCount getUserActivityCount(Long uid) {
        return userActivityCountRepository.findByUserId(uid)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER_ACTIVITY));
    }

    @Async
    public CompletableFuture<UserActivityCount> asyncGetUserActivityCount(Long uid) {
        return CompletableFuture.completedFuture(getUserActivityCount(uid));
    }

    public UserActivityCount getUserActivityCountOrGenerate(Long uid) {
        return userActivityCountRepository.findByUserId(uid).orElseGet(() -> createActivityCount(uid));
    }

    private UserActivityCount createActivityCount(long userId) {
        int quizCount = Math.toIntExact(quizReplyHistoryRepository.countByCreatedBy(userId));
        UserActivityCount u = new UserActivityCount(userId, userId,0, quizCount);
        return userActivityCountRepository.save(u);
    }
}
