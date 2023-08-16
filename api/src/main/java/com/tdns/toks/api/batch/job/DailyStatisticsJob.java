package com.tdns.toks.api.batch.job;

import com.tdns.toks.api.domain.actionlog.SystemActionLogService;
import com.tdns.toks.api.domain.cruiser.client.CruiserClient;
import com.tdns.toks.api.domain.cruiser.dto.CruiserRequest;
import com.tdns.toks.api.domain.user.service.UserService;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DailyStatisticsJob {
    private final CruiserClient cruiserClient;
    private final UserService userService;
    private final SystemActionLogService systemActionLogService;
    private final QuizRepository quizRepository;
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;

    public void dailyStatisticsJob() {
        var now = LocalDateTime.now();

        var userCount = userService.countAllUsers();
        var newUserCount = userService.countNewUsers();
        var apiCallCount = systemActionLogService.countApiCallCount();

        var quizCount = quizRepository.count();
        var newQuizCount = quizRepository.countByCreatedAtBetween(now.minusDays(1), now);

        var replyCount = quizReplyHistoryRepository.count();
        var newReplyCount = quizReplyHistoryRepository.countByCreatedAtBetween(now.minusDays(1), now);

        var request = new CruiserRequest(
                ":pray: *극락 알림* :pray:"
                        + "\n- 전체 가입자 : " + userCount
                        + "\n- 신규 가입자 : " + newUserCount
                        + "\n- 전체 퀴즈수 : " + quizCount
                        + "\n- 신규 퀴즈수 : " + newQuizCount
                        + "\n- 퀴즈 풀이수 : " + replyCount
                        + "\n- 신규 풀이수 : " + newReplyCount
                        + "\n- Api Call Count : " + apiCallCount
        );

        cruiserClient.send(request);
    }
}
