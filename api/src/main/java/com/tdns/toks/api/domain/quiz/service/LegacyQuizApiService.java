package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.core.domain.quiz.service.LegacyQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizSimpleResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LegacyQuizApiService {
    private final LegacyQuizService quizService;

    public QuizSimpleResponse getById(final Long id) {
        var quiz = quizService.retrieveByIdOrThrow(id);
        return QuizSimpleResponse.toResponse(quiz);
    }
}
