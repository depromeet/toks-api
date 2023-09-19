package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.api.domain.quiz.model.QuizInfoModel;
import com.tdns.toks.api.domain.quiz.model.QuizModel;
import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSearchRequest;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSolveDto;
import com.tdns.toks.api.domain.user.service.UserActivityCountService;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.AuthUserValidator;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.user.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

import static com.tdns.toks.core.common.utils.HttpUtil.getClientIpAddress;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CacheService cacheService;
    private final QuizCacheService quizCacheService;
    private final QuizReplyHistoryService quizReplyHistoryService;
    private final QuizInfoService quizInfoService;
    private final UserActivityCountService userActivityCountService;
    private final UserCategoryRepository userCategoryRepository;


    @SneakyThrows
    public QuizDetailResponse getDetail(
            @Nullable AuthUser authUser,
            Long quizId,
            HttpServletRequest httpServletRequest
    ) {
        var uid = AuthUserValidator.getUidOrElseDefault(authUser);

        var quizModelInfoCf = quizInfoService.asyncGetQuizInfoModelByQuizId(quizId);
        var quizReplyModelCf = quizReplyHistoryService.asyncGetReplyModel(
                uid,
                quizId,
                httpServletRequest
        );
        var quizReplyCountsModelCf = quizReplyHistoryService.asyncGetQuizReplyStatistics(quizId);

        CompletableFuture.allOf(quizModelInfoCf, quizReplyModelCf, quizReplyCountsModelCf).join();

        return QuizDetailResponse.of(
                quizModelInfoCf.get(),
                quizReplyModelCf.get(),
                quizReplyCountsModelCf.get()
        );
    }

    public Page<QuizInfoModel> search(@Nullable AuthUser authUser, QuizSearchRequest request) {
        validateQuizSearchRequest(request);

        var pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(Sort.Order.desc("createdAt"))
        );

        if (request.getCategoryIds() != null) {
            return quizRepository.findAllByCategoryIdInAndDeleted(request.getCategoryIds(), false, pageable)
                    .map(quizInfoService::getQuizInfoModelByQuiz);
        }

        if (AuthUserValidator.isAuthenticated(authUser)) {
            var userCategory = userCategoryRepository.findByUserId(authUser.getId());

            if (userCategory.isPresent()) {
                var userCategories = new HashSet<>(userCategory.get().getCategoryIds());
                return quizRepository.findAllByCategoryIdInAndDeleted(userCategories, false, pageable)
                        .map(quizInfoService::getQuizInfoModelByQuiz);
            } else {
                return quizRepository.findAllByDeletedOrderByCreatedAtDesc(false, pageable)
                        .map(quizInfoService::getQuizInfoModelByQuiz);
            }
        }

        return quizRepository.findAllByDeletedOrderByCreatedAtDesc(false, pageable)
                .map(quizInfoService::getQuizInfoModelByQuiz);
    }

    private void validateQuizSearchRequest(QuizSearchRequest request) {
        if (request.getSize() < 0 || request.getSize() > 50) {
            throw new ApplicationErrorException(ApplicationErrorType.INVALID_QUIZ_SEARCH_ERROR);
        }
    }

    @SneakyThrows
    @Transactional
    public QuizSolveDto.QuizSolveResponse solveQuiz(
            @Nullable AuthUser authUser,
            Long quizId,
            QuizSolveDto.QuizSolveRequest request,
            HttpServletRequest httpServletRequest
    ) {
        var quiz = quizCacheService.getCachedQuiz(quizId);
        var clientIp = getClientIpAddress(httpServletRequest);

        var uid = AuthUserValidator.getUidOrElseDefault(authUser);

        if (quizReplyHistoryService.isSubmitted(uid, quizId, httpServletRequest)) {
            throw new ApplicationErrorException(ApplicationErrorType.ALREADY_SUBMITTED_USER_QUIZ);
        }

        var quizReplyCountCf = quizReplyHistoryService.asyncCountByQuizIdAndAnswer(quizId, request.getAnswer());

        quizReplyHistoryService.save(uid, quizId, request.getAnswer(), clientIp);

        cacheService.asyncIncrement(CacheFactory.makeCachedQuizReplyHistoryCount(quizId));

        if (uid != -1) {
            userActivityCountService.getUserActivityCountOrGenerate(uid).updateTotalSolveCount();
        }

        quizReplyCountCf.join();

        return QuizSolveDto.QuizSolveResponse.of(quizReplyCountCf.get(), quiz);
    }

    public QuizModel getQuizModelOrThrow(Long quizId) {
        return quizRepository.findQuizByIdAndDeleted(quizId, false)
                .map(QuizModel::from)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR));
    }
}
