package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuizReplyHistoryService {
    private final CacheService cacheService;

    public Integer count(long quizId) {
        var count = cacheService.getOrNull(CacheFactory.makeCachedQuizReplyHistoryCount(quizId));

        if (count == null) {
            return 0;
        }

        return count;
    }

    @Async
    public CompletableFuture<Integer> asyncCount(long quizId) {
        return CompletableFuture.completedFuture(count(quizId));
    }
}
