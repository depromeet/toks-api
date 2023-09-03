package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.api.domain.category.service.CategoryService;
import com.tdns.toks.api.domain.quiz.model.QuizInfoModel;
import com.tdns.toks.api.domain.quiz.model.QuizModel;
import com.tdns.toks.core.domain.quiz.entity.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuizInfoService {
    private final CategoryService categoryService;
    private final QuizReplyHistoryService quizReplyHistoryService;
    private final CacheService cacheService;
    private final QuizCacheService quizCacheService;

    public QuizInfoModel getQuizInfoModelByQuizId(Long quizId) {
        var quiz = quizCacheService.getCachedQuiz(quizId);
        var category = categoryService.getOrThrow(quiz.getCategoryId());
        var quizCommentCount = cacheService.count(CacheFactory.makeCachedQuizCommentCount(quizId));
        var answerReplyCountCf = quizReplyHistoryService.asyncCountByQuizIdAndAnswer(quizId, quiz.getAnswer());
        var quizReplyHistoryCount = cacheService.count(CacheFactory.makeCachedQuizReplyHistoryCount(quizId));

        return QuizInfoModel.of(
                quiz,
                category,
                quizReplyHistoryCount,
                answerReplyCountCf.join(),
                quizCommentCount
        );
    }

    @Async(value = "taskExecutor")
    public CompletableFuture<QuizInfoModel> asyncGetQuizInfoModelByQuizId(Long quizId) {
        return CompletableFuture.completedFuture(getQuizInfoModelByQuizId(quizId));
    }

    // TODO : 퀴즈 검색 조회, 추천 데이터 조회 - DB 한번만 찌르게 수정
    public QuizInfoModel getQuizInfoModelByQuiz(Quiz quiz) {
        var category = categoryService.getOrThrow(quiz.getCategoryId());
        var quizReplyHistoryCount = cacheService.count(CacheFactory.makeCachedQuizReplyHistoryCount(quiz.getId()));

        // TODO Refacor
        var answerReplyCount = quizReplyHistoryService.countByQuizIdAndAnswer(quiz.getId(), quiz.getAnswer());
        var quizCommentCount = cacheService.count(CacheFactory.makeCachedQuizCommentCount(quiz.getId()));

        return QuizInfoModel.of(
                QuizModel.from(quiz),
                category,
                quizReplyHistoryCount,
                answerReplyCount,
                quizCommentCount
        );
    }
}
