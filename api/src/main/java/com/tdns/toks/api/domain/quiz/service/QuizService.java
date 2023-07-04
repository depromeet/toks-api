package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSimpleResponse;
import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizDetailResponse get(AuthUser authUser, Long quizId) {
        var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("error")); // TODO

        return QuizMapper.toQuizResponse(quiz);
    }

    public Page<QuizSimpleResponse> getAll(
            AuthUser authUser,
            Set<String> categoryIds,
            Integer page,
            Integer size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return quizRepository.findAllByCategoryIdIn(categoryIds, pageable)
                .map(QuizSimpleResponse::from);
    }
}
