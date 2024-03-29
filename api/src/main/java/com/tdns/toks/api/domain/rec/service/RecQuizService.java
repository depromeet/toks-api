package com.tdns.toks.api.domain.rec.service;

import com.tdns.toks.api.domain.quiz.service.QuizCacheService;
import com.tdns.toks.api.domain.quiz.service.QuizInfoService;
import com.tdns.toks.api.domain.rec.model.dto.QuizRecResponse;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.entity.Quiz;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.rec.repository.RecQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecQuizService {
    private final QuizRepository quizRepository;
    private final QuizInfoService quizInfoService;
    private final RecQuizRepository recQuizRepository;
    private final QuizCacheService quizCacheService;

    /**
     * 초기 기획에서는 지정된 추천 정보만 사용자에게 제공한다.
     * round는 총 3개 이고, 랜덤하게 뽑아서 사용 진행 추후, 추천 정보 제공을 위한 알고리즘 개발 진행 response의 개수는 3개를 보장한다.
     */
    public QuizRecResponse getRecQuizModels(@Nullable AuthUser authUser, Long quizId) {
        var quizModel = quizCacheService.getCachedQuiz(quizId);
        var randomRound = new Random().nextInt(2) + 1;

        var quizzes = recQuizRepository.findByRoundAndCategoryId(randomRound, quizModel.getCategoryId())
                .map(quiz -> quizRepository.findAllById(quiz.getPids()))
                .orElseGet(() -> quizRepository.findTop3ByCategoryId(quizModel.getCategoryId()))
                .stream()
                .filter(q -> !q.getId().equals(quizId))
                .collect(Collectors.toList());

        if (quizzes.size() != 3) {
            var quizIds = quizzes
                    .stream()
                    .map(Quiz::getId)
                    .collect(Collectors.toList());

            quizzes.add(quizRepository.findTop1ByIdIsNotIn(quizIds));
        }
        
        var recQuizModels = quizzes.stream()
                .map(quizInfoService::getQuizInfoModelByQuiz)
                .collect(Collectors.toList());

        return new QuizRecResponse(recQuizModels);
    }
}
