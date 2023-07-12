package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizReplyHistoryCacheService {
    private final CacheService cacheService;

    public Integer count(long quizId) {
        var count = cacheService.getOrNull(CacheFactory.makeCachedQuizReplyHistoryCount(quizId));
        return count != null ? count : 0;
    }
}
