package com.tdns.toks.api.domain.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuizReplyHistoryService {
    private final StringRedisTemplate redisTemplate;

    public final static String QUIZ_REPLY_HISTORY_CACHE = "quiz:reply-history:count:";

    public Integer count(long quizId) {
        var count = redisTemplate.opsForValue().get(
                QUIZ_REPLY_HISTORY_CACHE + quizId
        );

        if (count == null) {
            return 0;
        }

        return Integer.parseInt(count);
    }

    @Async
    public CompletableFuture<Integer> asyncCount(long quizId) {
        return CompletableFuture.completedFuture(count(quizId));
    }
}
