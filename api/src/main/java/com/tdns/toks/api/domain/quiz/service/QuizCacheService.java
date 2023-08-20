package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.api.domain.quiz.model.QuizModel;
import com.tdns.toks.core.domain.quiz.entity.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizCacheService {
    private final QuizService quizService;
    private final CacheService cacheService;

    public QuizModel getCachedQuiz(Long quizId) {
        var cache = CacheFactory.makeCachedQuiz(quizId);
        return cacheService.get(cache, () -> quizService.getQuizModelOrThrow(quizId));
    }

    public void setCachedQuiz(Quiz quiz) {
        var cache = CacheFactory.makeCachedQuiz(quiz.getId());
        cacheService.set(cache, QuizModel.from(quiz));
    }

    public void deleteCachedQuiz(Long quizId) {
        var cache = CacheFactory.makeCachedQuiz(quizId);
        cacheService.delete(cache);
    }

    public void incrementQuizReply(Long quizId) {
        var cache = CacheFactory.makeCachedQuizReplyHistoryCount(quizId);
        cacheService.increment(cache);
    }
}
