package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.category.service.CategoryService;
import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import com.tdns.toks.api.domain.quiz.model.QuizSimpleModel;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSolveDto;
import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.entity.Quiz;
import com.tdns.toks.core.domain.quiz.entity.QuizReplyHistory;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static com.tdns.toks.core.common.utils.HttpUtil.getClientIpAddress;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CategoryService categoryService;
    private final QuizCommentService quizCommentService;
    private final QuizReplyHistoryCacheService quizReplyHistoryCacheService;
    private final QuizReplyHistoryService quizReplyHistoryService;
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;
    private final QuizCacheService quizCacheService;

    @SneakyThrows
    public QuizDetailResponse get(AuthUser authUser, Long quizId, HttpServletRequest httpServletRequest) {
        var quiz = quizCacheService.getCachedQuiz(quizId);
        var category = categoryService.get(quiz.getCategoryId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

        var isSubmittedCf = quizReplyHistoryService.asyncIsSubmitted(authUser, quizId, httpServletRequest);

        var quizReplyHistoryCount = quizReplyHistoryCacheService.count(quizId);
        var quizCommentCount = quizCommentService.count(quizId);

        return QuizMapper.toQuizResponse(
                quiz,
                category,
                quizReplyHistoryCount,
                quizCommentCount,
                isSubmittedCf.get()
        );
    }

    public Page<QuizSimpleModel> getAll(
            AuthUser authUser,
            Set<String> categoryIds,
            Integer page,
            Integer size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return quizRepository.findAllByCategoryIdIn(categoryIds, pageable).map(this::resolveQuizSimpleDetail);
    }

    public QuizSimpleModel resolveQuizSimpleDetail(Quiz quiz) {
        var category = categoryService.get(quiz.getCategoryId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

        var quizReplyHistoryCount = quizReplyHistoryCacheService.count(quiz.getId());
        var quizCommentCount = quizCommentService.count(quiz.getId());

        return QuizMapper.toQuizSimpleResponse(quiz, category, quizReplyHistoryCount, quizCommentCount);
    }

    @SneakyThrows
    @Transactional
    public QuizSolveDto.QuizSolveResponse solveQuiz(
            AuthUser authUser,
            Long quizId,
            QuizSolveDto.QuizSolveRequest request,
            HttpServletRequest httpServletRequest
    ) {
        var quiz = quizCacheService.getCachedQuiz(quizId);
        var clientIp = getClientIpAddress(httpServletRequest);

        var isSubmitted = quizReplyHistoryService.isSubmitted(authUser, quizId, httpServletRequest);

        if (isSubmitted) {
            throw new ApplicationErrorException(ApplicationErrorType.ALREADY_SUBMITTED_USER_QUIZ);
        }

        var quizReplyCountCf = quizReplyHistoryService.asyncCountByQuizIdAndAnswer(quizId, request.getAnswer());

        quizReplyHistoryRepository.save(QuizReplyHistory.builder()
                .quizId(quizId)
                .answer(request.getAnswer())
                .ipAddress(authUser == null ? clientIp : null)
                .createdBy(authUser == null ? null : authUser.getId())
                .build());

        quizReplyCountCf.join();

        return new QuizSolveDto.QuizSolveResponse(quizReplyCountCf.get(), quiz.getDescription());
    }
}
