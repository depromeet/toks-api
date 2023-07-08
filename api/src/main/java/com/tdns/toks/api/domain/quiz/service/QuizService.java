package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.category.service.CategoryService;
import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSimpleResponse;
import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CategoryService categoryService;

    private final QuizCommentService quizCommentService;
    private final QuizReplyHistoryService quizReplyHistoryService;

    @SneakyThrows
    public QuizDetailResponse get(AuthUser authUser, Long quizId) {
        // TODO : caching
        var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR));

        var category = categoryService.get(quiz.getCategoryId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

        var quizReplyHistoryCount = quizReplyHistoryService.asyncCount(quizId);
        var quizCommentCount = quizCommentService.asyncCount(quizId);

        CompletableFuture.allOf(quizReplyHistoryCount, quizCommentCount).join();

        return QuizMapper.toQuizResponse(quiz, category, quizReplyHistoryCount.get(), quizCommentCount.get());
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
