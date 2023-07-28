package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.QuizInfoModel;
import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSolveDto;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
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
import java.util.concurrent.CompletableFuture;

import static com.tdns.toks.core.common.utils.HttpUtil.getClientIpAddress;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizReplyHistoryService quizReplyHistoryService;
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;
    private final QuizCacheService quizCacheService;
    private final QuizInfoService quizInfoService;

    @SneakyThrows
    public QuizDetailResponse get(AuthUser authUser, Long quizId, HttpServletRequest httpServletRequest) {
        var quizModelInfoCf = quizInfoService.asyncGetQuizInfoModelByQuizId(quizId);
        var isSubmittedCf = quizReplyHistoryService.asyncIsSubmitted(authUser, quizId, httpServletRequest);

        CompletableFuture.allOf(quizModelInfoCf, isSubmittedCf).join();

        return QuizDetailResponse.of(quizModelInfoCf.get(), isSubmittedCf.get());
    }

    public Page<QuizInfoModel> getAll(
            AuthUser authUser,
            Set<String> categoryIds,
            Integer page,
            Integer size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return quizRepository.findAllByCategoryIdIn(categoryIds, pageable).map(quizInfoService::getQuizInfoModelByQuiz);
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

        quizCacheService.incrementQuizReply(quizId);

        return new QuizSolveDto.QuizSolveResponse(quizReplyCountCf.get(), quiz.getDescription());
    }
}
