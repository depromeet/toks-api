package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.api.domain.quiz.model.QuizModel;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.quiz.entity.Quiz;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizCacheService {
    private final QuizRepository quizRepository;
    private final CacheService cacheService;

    public QuizModel getCachedQuiz(Long quizId) {
        var cache = CacheFactory.makeCachedQuiz(quizId);
        var quizModel = cacheService.getOrNull(cache);

        if (quizModel == null) {
            var quiz = quizRepository.findQuizByIdAndDeleted(quizId, false)
                    .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR));

            var newQuizModel = new QuizModel(
                    quiz.getId(),
                    quiz.getCategoryId(),
                    quiz.getTitle(),
                    quiz.getQuestion(),
                    quiz.getQuizType(),
                    quiz.getDescription(),
                    quiz.getAnswer()
            );

            cacheService.set(cache, newQuizModel);

            return newQuizModel;
        } else {
            return quizModel;
        }
    }

    public void setCachedQuiz(Quiz quiz) {
        var cache = CacheFactory.makeCachedQuiz(quiz.getId());

        var newQuizModel = new QuizModel(
                quiz.getId(),
                quiz.getCategoryId(),
                quiz.getTitle(),
                quiz.getQuestion(),
                quiz.getQuizType(),
                quiz.getDescription(),
                quiz.getAnswer()
        );

        cacheService.set(cache, newQuizModel);
    }

    public void deleteCachedQuiz(Long quizId) {
        var cache = CacheFactory.makeCachedQuiz(quizId);
        cacheService.delete(cache);
    }
}
