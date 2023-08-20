package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.category.service.CategoryService;
import com.tdns.toks.api.domain.quiz.model.QuizInfoModel;
import com.tdns.toks.core.domain.quiz.entity.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuizInfoService {
    private final CategoryService categoryService;
    private final QuizCommentService quizCommentService;
    private final QuizCacheService quizCacheService;

    public QuizInfoModel getQuizInfoModelByQuizId(Long quizId) {
        var quiz = quizCacheService.getCachedQuiz(quizId);
        var category = categoryService.get(quiz.getCategoryId());
        var quizCommentCount = quizCommentService.count(quizId);

        return QuizInfoModel.of(quiz, category, quizCommentCount);
    }

    @Async
    public CompletableFuture<QuizInfoModel> asyncGetQuizInfoModelByQuizId(Long quizId) {
        return CompletableFuture.completedFuture(getQuizInfoModelByQuizId(quizId));
    }

    public QuizInfoModel getQuizInfoModelByQuiz(Quiz quiz) {
        var category = categoryService.get(quiz.getCategoryId());
        var quizCommentCount = quizCommentService.count(quiz.getId());

        return QuizInfoModel.of(quiz, category, quizCommentCount);
    }
}
