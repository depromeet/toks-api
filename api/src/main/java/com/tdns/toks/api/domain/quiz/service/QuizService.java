package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.category.service.CategoryService;
import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import com.tdns.toks.api.domain.quiz.model.dto.QuizRecModel;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSoleDto;
import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.model.entity.QuizReplyHistory;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.rec.repository.RecQuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tdns.toks.api.util.HttpUtil.getClientIp;

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
    private final RecQuizRepository recQuizRepository;

    @SneakyThrows
    public QuizDetailResponse get(AuthUser authUser, Long quizId) {
        var quiz = quizCacheService.getCachedQuiz(quizId);
        var category = categoryService.get(quiz.getCategoryId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

        var quizReplyHistoryCount = quizReplyHistoryCacheService.count(quizId);
        var quizCommentCount = quizCommentService.count(quizId);

        return QuizMapper.toQuizResponse(quiz, category, quizReplyHistoryCount, quizCommentCount);
    }

    public Page<QuizDetailResponse> getAll(
            AuthUser authUser,
            Set<String> categoryIds,
            Integer page,
            Integer size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return quizRepository.findAllByCategoryIdIn(categoryIds, pageable).map(this::resolveQuizDetail);
    }

    /**
     * 초기 기획에서는 지정된 추천 정보만 사용자에게 제공한다.
     * - round는 총 3개 이고, 랜덤하게 뽑아서 사용 진행
     * - 추후, 추천 정보 제공을 위한 알고리즘 개발 진행
     */
    public QuizRecModel getRecModels(AuthUser authUser, String categoryId) {
        var randomRound = new Random().nextInt(2) + 1;

        var recQuizzes = recQuizRepository.findByRoundAndCategoryId(randomRound, categoryId)
                .map(quiz -> quizRepository.findAllById(quiz.getPids()))
                .orElseGet(() -> quizRepository.findTop3ByCategoryId(categoryId));

        var recQuizModels = recQuizzes.stream()
                .map(this::resolveQuizDetail)
                .collect(Collectors.toList());

        return new QuizRecModel(recQuizModels);
    }

    private QuizDetailResponse resolveQuizDetail(Quiz quiz) {
        var category = categoryService.get(quiz.getCategoryId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

        var quizReplyHistoryCount = quizReplyHistoryCacheService.count(quiz.getId());
        var quizCommentCount = quizCommentService.count(quiz.getId());

        return QuizMapper.toQuizResponse(quiz, category, quizReplyHistoryCount, quizCommentCount);
    }

    @SneakyThrows
    @Transactional
    public QuizSoleDto.QuizSolveResponse solveQuiz(
            AuthUser authUser,
            Long quizId,
            QuizSoleDto.QuizSolveRequest request,
            HttpServletRequest httpServletRequest
    ) {
        var quiz = quizCacheService.getCachedQuiz(quizId);
        var clientIp = getClientIp(httpServletRequest);

        var isSubmitted = (authUser == null)
                ? (clientIp != null && quizReplyHistoryRepository.existsByQuizIdAndIpAddress(quizId, clientIp))
                : quizReplyHistoryRepository.existsByQuizIdAndCreatedBy(quizId, authUser.getId());

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

        return new QuizSoleDto.QuizSolveResponse(quizReplyCountCf.get(), quiz.getDescription());
    }
}
