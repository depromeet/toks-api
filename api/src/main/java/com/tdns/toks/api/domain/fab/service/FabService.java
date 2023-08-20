package com.tdns.toks.api.domain.fab.service;

import com.tdns.toks.api.config.FabProperties;
import com.tdns.toks.api.domain.fab.model.FabModel;
import com.tdns.toks.api.domain.fab.model.dto.FabDto;
import com.tdns.toks.api.domain.quiz.service.QuizReplyHistoryService;
import com.tdns.toks.api.domain.user.service.UserActivityCountService;
import com.tdns.toks.api.domain.user.service.UserService;
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
    private final FabProperties fabProperties;
    private final UserService userService;

    @SneakyThrows
    public FabDto.GetFabResponseDto getFabInfo(AuthUser authUser, int month, int year) {
        var userActivityCountCf = userActivityCountService.asyncGetUserActivityCountOrGenerate(authUser.getId());
        var userMonthlySolveActivityCf = quizReplyHistoryService.asyncGetUserMonthlySolveActivity(
                authUser.getId(),
                month,
                year
        );
        var userModelCf = userService.asyncGetUserModel(authUser.getId());
        var todaySolveCountCf = quizReplyHistoryService.asyncGetTodaySolveCount(authUser.getId());

        CompletableFuture.allOf(userActivityCountCf, userMonthlySolveActivityCf, userModelCf, todaySolveCountCf).join();

        var fabModel = FabModel.of(
                userActivityCountCf.get(),
                userMonthlySolveActivityCf.get(),
                userModelCf.get(),
                todaySolveCountCf.get()
        );

        return resolveFabResponse(fabModel);
    }

    private FabDto.GetFabResponseDto resolveFabResponse(FabModel model) {
        var title = fabProperties.getTitle().replace("{username}", model.getUserName())
                .replace("{totalVisitCount}", String.valueOf(model.getTotalVisitCount()));
        var description1 = fabProperties.getDescription1().replace("{todaySolveCount}", String.valueOf(model.getTodaySolveCount()));
        var description2 = fabProperties.getDescription2().replace("{totalSolveCount}", String.valueOf(model.getTotalSolveCount()));

        return new FabDto.GetFabResponseDto(title, description1, description2, model.getMonthlySolveCount());
    }
}
