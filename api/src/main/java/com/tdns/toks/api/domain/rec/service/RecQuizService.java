package com.tdns.toks.api.domain.rec.service;

import com.tdns.toks.api.domain.quiz.service.QuizService;
import com.tdns.toks.api.domain.rec.model.dto.QuizRecResponse;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.rec.repository.RecQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecQuizService {
    private final QuizRepository quizRepository;
    private final QuizService quizService;
    private final RecQuizRepository recQuizRepository;

    /**
     * 초기 기획에서는 지정된 추천 정보만 사용자에게 제공한다.
     * - round는 총 3개 이고, 랜덤하게 뽑아서 사용 진행
     * - 추후, 추천 정보 제공을 위한 알고리즘 개발 진행
     */
    public QuizRecResponse getRecModels(AuthUser authUser, String categoryId) {
        var randomRound = new Random().nextInt(2) + 1;

        var recQuizzes = recQuizRepository.findByRoundAndCategoryId(randomRound, categoryId)
                .map(quiz -> quizRepository.findAllById(quiz.getPids()))
                .orElseGet(() -> quizRepository.findTop3ByCategoryId(categoryId));

        var recQuizModels = recQuizzes.stream()
                .map(quizService::resolveQuizSimpleDetail)
                .collect(Collectors.toList());

        return new QuizRecResponse(recQuizModels);
    }
}
