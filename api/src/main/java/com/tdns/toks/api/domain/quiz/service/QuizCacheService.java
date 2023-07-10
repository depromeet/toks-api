package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.api.domain.quiz.model.dto.QuizModel;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.utils.MapperUtil;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

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
                    MapperUtil.readValue(quiz.getQuestion(), Map.class),
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
}
