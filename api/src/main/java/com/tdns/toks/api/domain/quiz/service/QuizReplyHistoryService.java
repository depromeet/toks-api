package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.fab.model.DailySolveCountModel;
import com.tdns.toks.api.domain.quiz.model.QuizReplyCountsModel;
import com.tdns.toks.api.domain.quiz.model.QuizReplyModel;
import com.tdns.toks.core.domain.quiz.entity.QuizReplyHistory;
import com.tdns.toks.core.domain.quiz.model.QuizReplyCountModel;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.tdns.toks.core.common.utils.HttpUtil.getClientIpAddress;

@Service
@RequiredArgsConstructor
public class QuizReplyHistoryService {
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;
    private final QuizCacheService quizCacheService;

    public Long countByQuizIdAndAnswer(Long quizId, String answer) {
        return quizReplyHistoryRepository.countByQuizIdAndAnswer(quizId, answer);
    }

    @Async(value = "taskExecutor")
    public CompletableFuture<Long> asyncCountByQuizIdAndAnswer(Long quizId, String answer) {
        return CompletableFuture.completedFuture(countByQuizIdAndAnswer(quizId, answer));
    }

    public boolean isSubmitted(Long uid, Long quizId, HttpServletRequest httpServletRequest) {
        if (uid != -1) {
            return quizReplyHistoryRepository.existsByQuizIdAndCreatedBy(quizId, uid);
        }

        var clientIp = getClientIpAddress(httpServletRequest);
        return clientIp != null && quizReplyHistoryRepository.existsByQuizIdAndIpAddress(quizId, clientIp);
    }

    public QuizReplyModel getReplyModel(Long uid, Long quizId, HttpServletRequest httpServletRequest) {
        if (uid != -1) {
            return quizReplyHistoryRepository.findByQuizIdAndCreatedBy(quizId, uid)
                    .map(QuizReplyModel::from).orElse(null);
        }

        var clientIp = getClientIpAddress(httpServletRequest);
        if (clientIp != null) {
            return quizReplyHistoryRepository.findByQuizIdAndIpAddress(quizId, clientIp)
                    .map(QuizReplyModel::from).orElse(null);
        }

        return null;
    }

    @Async(value = "taskExecutor")
    public CompletableFuture<QuizReplyModel> asyncGetReplyModel(
            Long uid,
            Long quizId,
            HttpServletRequest httpServletRequest
    ) {
        return CompletableFuture.completedFuture(getReplyModel(uid, quizId, httpServletRequest));
    }

    @Transactional
    public QuizReplyHistory save(Long uid, Long quizId, String answer, String clientIp) {
        return quizReplyHistoryRepository.save(
                QuizReplyHistory.builder()
                        .quizId(quizId)
                        .answer(answer)
                        .ipAddress(clientIp)
                        .createdBy(uid)
                        .build()
        );
    }

    public QuizReplyCountsModel getQuizReplyStatistics(Long quizId) {
        var quizModel = quizCacheService.getCachedQuiz(quizId);

        var answer = QuizType.getAnswer(quizModel.getQuizType());

        var quizReplyHistoryModel = quizReplyHistoryRepository.findQuizReplyCount(quizModel.getId(), answer)
                .stream()
                .filter(model -> (model.getAnswer() != null && model.getCount() != null))
                .collect(Collectors.toList());

        var totalCount = quizReplyHistoryModel.stream()
                .mapToLong(QuizReplyCountModel::getCount).sum();

        var statistics = quizReplyHistoryModel.stream()
                .map(QuizReplyCountsModel.ReplyModel::from)
                .collect(Collectors.toMap(QuizReplyCountsModel.ReplyModel::getAnswer, Function.identity()));

        return new QuizReplyCountsModel(totalCount, statistics);
    }

    @Async(value = "taskExecutor")
    public CompletableFuture<QuizReplyCountsModel> asyncGetQuizReplyStatistics(Long quizId) {
        return CompletableFuture.completedFuture(getQuizReplyStatistics(quizId));
    }

    public List<DailySolveCountModel> getUserMonthlySolveActivity(Long uid, int year, int month) {
        return quizReplyHistoryRepository.findUserMonthlySolveActivity(uid, year, month)
                .stream()
                .map(DailySolveCountModel::from)
                .collect(Collectors.toList());
    }

    @Async(value = "taskExecutor")
    public CompletableFuture<List<DailySolveCountModel>> asyncGetUserMonthlySolveActivity(Long uid, int year, int month) {
        return CompletableFuture.completedFuture(getUserMonthlySolveActivity(uid, year, month));
    }

    public Integer getTodaySolveCount(Long uid) {
        return Math.toIntExact(
                quizReplyHistoryRepository.countUserDailySolveActivity(
                        uid,
                        LocalDate.now()
                )
        );
    }

    @Async(value = "taskExecutor")
    public CompletableFuture<Integer> asyncGetTodaySolveCount(Long uid) {
        return CompletableFuture.completedFuture(getTodaySolveCount(uid));
    }
}
