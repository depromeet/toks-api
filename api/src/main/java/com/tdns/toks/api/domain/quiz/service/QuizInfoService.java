package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.category.service.CategoryService;
import com.tdns.toks.api.domain.quiz.model.QuizInfoModel;
import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
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
    private final QuizCommentService quizCommentService;
    private final QuizCacheService quizCacheService;

    public QuizInfoModel getQuizInfoModelByQuizId(Long quizId) {
        var quiz = quizCacheService.getCachedQuiz(quizId);
        CompletableFuture<Long> longCompletableFuture = quizReplyHistoryService.asyncCountByQuizIdAndAnswer(quiz.getId(), quiz.getAnswer());
        var category = categoryService.get(quiz.getCategoryId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

        var quizReplyHistoryCount = quizReplyHistoryService.count(quiz.getId());
        var quizCommentCount = quizCommentService.count(quizId);
        Long answerReplyCount = longCompletableFuture.join();
        return new QuizInfoModel(quiz, category, quizReplyHistoryCount, answerReplyCount, quizCommentCount);
    }

    @Async
    public CompletableFuture<QuizInfoModel> asyncGetQuizInfoModelByQuizId(Long quizId) {
        return CompletableFuture.completedFuture(getQuizInfoModelByQuizId(quizId));
    }

    public QuizInfoModel getQuizInfoModelByQuiz(Quiz quiz) {
        var category = categoryService.get(quiz.getCategoryId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

        var quizReplyHistoryCount = quizReplyHistoryService.count(quiz.getId());
        var answerCount = quizReplyHistoryService.countByQuizIdAndAnswer(quiz.getId(), quiz.getAnswer());
        var quizCommentCount = quizCommentService.count(quiz.getId());

        return QuizMapper.toQuizInfoResponse(quiz, category, quizReplyHistoryCount,answerCount, quizCommentCount);
    }
}
