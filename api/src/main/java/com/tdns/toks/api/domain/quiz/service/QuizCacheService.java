package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.dto.QuizModel;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.utils.MapperUtil;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizCacheService {
    private final QuizRepository quizRepository;
    private final StringRedisTemplate redisTemplate;

    public final static String QUIZ_MODEL_CACHE = "quiz:model:";

    // TODO : cache 처리 용 제네릭 오브젝트 만들기..
    public QuizModel getCachedQuiz(Long quizId) {
        var key = QUIZ_MODEL_CACHE + quizId;
        var quizModel = redisTemplate.opsForValue().get(key);

        if (quizModel == null) {
            var quiz = quizRepository.findById(quizId)
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

            redisTemplate.opsForValue().set(key, MapperUtil.writeValueAsString(newQuizModel), Duration.ofMinutes(3L));

            return newQuizModel;
        } else {
            return MapperUtil.readValue(quizModel, QuizModel.class);
        }
    }
}
