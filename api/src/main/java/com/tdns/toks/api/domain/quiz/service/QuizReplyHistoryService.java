package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.api.domain.quiz.model.QuizReplyCountsModel;
import com.tdns.toks.api.domain.quiz.model.QuizReplyModel;
import com.tdns.toks.core.domain.auth.AuthUserValidator;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.entity.QuizReplyHistory;
import com.tdns.toks.core.domain.quiz.model.QuizReplyCountModel;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.tdns.toks.core.common.utils.HttpUtil.getClientIpAddress;

@Service
@RequiredArgsConstructor
public class QuizReplyHistoryService {
    private final CacheService cacheService;
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;
    private final QuizCacheService quizCacheService;

    public Long countByQuizIdAndAnswer(Long quizId, String answer) {
        return quizReplyHistoryRepository.countByQuizIdAndAnswer(quizId, answer);
    }

    @Async
    public CompletableFuture<Long> asyncCountByQuizIdAndAnswer(Long quizId, String answer) {
        return CompletableFuture.completedFuture(countByQuizIdAndAnswer(quizId, answer));
    }

    public boolean isSubmitted(@Nullable AuthUser authUser, Long quizId, HttpServletRequest httpServletRequest) {
        if (AuthUserValidator.isAuthenticated(authUser)) {
            return quizReplyHistoryRepository.existsByQuizIdAndCreatedBy(quizId, authUser.getId());
        }

        var clientIp = getClientIpAddress(httpServletRequest);
        return clientIp != null && quizReplyHistoryRepository.existsByQuizIdAndIpAddress(quizId, clientIp);
    }

    @Async
    public CompletableFuture<Boolean> asyncIsSubmitted(
            @Nullable AuthUser authUser,
            Long quizId,
            HttpServletRequest httpServletRequest
    ) {
        return CompletableFuture.completedFuture(isSubmitted(authUser, quizId, httpServletRequest));
    }

    public Integer count(long quizId) {
        var count = cacheService.getOrNull(CacheFactory.makeCachedQuizReplyHistoryCount(quizId));
        return count != null ? count : 0;
    }

    public CompletableFuture<Integer> asyncCount(long quizId) {
        return CompletableFuture.completedFuture(count(quizId));
    }


    public QuizReplyModel getReplyModel(@Nullable AuthUser authUser, Long quizId, HttpServletRequest httpServletRequest) {
        if (AuthUserValidator.isAuthenticated(authUser)) {
            var replyModel = quizReplyHistoryRepository.findByQuizIdAndCreatedBy(quizId, authUser.getId());
            if (replyModel.isPresent()) {
                return QuizReplyModel.from(replyModel.get());
            }
        }

        var clientIp = getClientIpAddress(httpServletRequest);
        if (clientIp != null) {
            var replyModel = quizReplyHistoryRepository.findByQuizIdAndIpAddress(quizId, clientIp);
            if (replyModel.isPresent()) {
                return QuizReplyModel.from(replyModel.get());
            }
        }

        return null;
    }

    @Async
    public CompletableFuture<QuizReplyModel> asyncGetReplyModel(
            @Nullable AuthUser authUser,
            Long quizId,
            HttpServletRequest httpServletRequest
    ) {
        return CompletableFuture.completedFuture(getReplyModel(authUser, quizId, httpServletRequest));
    }

    @Transactional
    public QuizReplyHistory save(AuthUser authUser, Long quizId, String answer, String clientIp) {
        return quizReplyHistoryRepository.save(
                QuizReplyHistory.builder()
                        .quizId(quizId)
                        .answer(answer)
                        .ipAddress(authUser == null ? clientIp : null)
                        .createdBy(authUser == null ? null : authUser.getId())
                        .build()
        );
    }

    public QuizReplyCountsModel getQuizReplyStatistics(Long quizId) {
        var quizModel = quizCacheService.getCachedQuiz(quizId);

        var quizReplyHistoryModel = quizReplyHistoryRepository.findQuizReplyCount(
                quizModel.getId(),
                QuizType.getAnswer(quizModel.getQuizType())
        );

        if (quizReplyHistoryModel.stream().anyMatch(a -> a.getCount() == null || a.getAnswer() == null)) {
            return null;
        }

        var totalCount = quizReplyHistoryModel.stream()
                .mapToInt(QuizReplyCountModel::getCount).sum();

        var statistics = quizReplyHistoryModel.stream()
                .map(reply -> new QuizReplyCountsModel.ReplyModel(
                                reply.getAnswer(),
                                reply.getCount()
                        )
                ).collect(Collectors.toMap(QuizReplyCountsModel.ReplyModel::getAnswer, Function.identity()));

        return new QuizReplyCountsModel(totalCount, statistics);
    }

    @Async
    public CompletableFuture<QuizReplyCountsModel> asyncGetQuizReplyStatistics(Long quizId) {
        return CompletableFuture.completedFuture(getQuizReplyStatistics(quizId));
    }
}
