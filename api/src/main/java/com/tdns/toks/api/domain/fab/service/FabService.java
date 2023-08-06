package com.tdns.toks.api.domain.fab.service;

import com.tdns.toks.api.domain.fab.model.FabModel;
import com.tdns.toks.api.domain.fab.model.dto.FabDto;
import com.tdns.toks.api.domain.quiz.service.QuizReplyHistoryService;
import com.tdns.toks.api.domain.user.service.UserActivityCountService;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FabService {
    private final UserActivityCountService userActivityCountService;
    private final QuizReplyHistoryService quizReplyHistoryService;

    @SneakyThrows
    public FabDto.GetFabResponseDto getFabInfo(AuthUser authUser, int month, int year) {
        var userActivityCountCf = userActivityCountService.asyncGetUserActivityCount(authUser.getId());
        var userMonthlySolveActivityCf = quizReplyHistoryService.asyncGetUserMonthlySolveActivity(
                authUser.getId(),
                month,
                year
        );
        var todaySolveCountCf = quizReplyHistoryService.asyncGetTodaySolveCount(authUser.getId());

        CompletableFuture.allOf(userActivityCountCf, userMonthlySolveActivityCf, todaySolveCountCf).join();

        var fabModel = FabModel.of(userActivityCountCf.get(), userMonthlySolveActivityCf.get(), todaySolveCountCf.get());

        return new FabDto.GetFabResponseDto(fabModel);
    }
}
