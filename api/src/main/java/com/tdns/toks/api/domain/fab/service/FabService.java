package com.tdns.toks.api.domain.fab.service;

import com.tdns.toks.api.domain.fab.model.DailySolveCountModel;
import com.tdns.toks.api.domain.fab.model.FabModel;
import com.tdns.toks.api.domain.fab.model.dto.FabDto;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.user.entity.UserActivityCount;
import com.tdns.toks.core.domain.user.model.UserDailySolveCountModel;
import com.tdns.toks.core.domain.user.repository.UserActivityCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FabService {

    private final UserActivityCountRepository userActivityCountRepository;
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;


    public FabDto.GetFabResponseDto getFabInfo(AuthUser authUser, int month, int year) {
        UserActivityCount userActivityCount = userActivityCountRepository.findByUserId(authUser.getId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER_ACTIVITY));

        List<UserDailySolveCountModel> userMonthlySolveActivity = quizReplyHistoryRepository.findUserMonthlySolveActivity(userActivityCount.getUserId(), month, year);

        int todaySolveCount = Math.toIntExact((quizReplyHistoryRepository.countUserDailySolveActivity(authUser.getId(), LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")))));

        FabModel fabModel = FabModel.builder()
                .userId(userActivityCount.getUserId())
                .totalVisitCount(userActivityCount.getTotalVisitCount())
                .totalSolveCount(userActivityCount.getTotalSolveCount())
                .todaySolveCount(todaySolveCount)
                .monthlySolveCount(getMonthlySolveCount(userMonthlySolveActivity))
                .build();
        return new FabDto.GetFabResponseDto(fabModel);
    }

    private List<DailySolveCountModel> getMonthlySolveCount(List<UserDailySolveCountModel> solveActivity) {
        List<DailySolveCountModel> monthlySolve = new ArrayList<>();

        for (UserDailySolveCountModel model : solveActivity) {
            monthlySolve.add(new DailySolveCountModel(model.getDate(), model.getValue()));
        }
        return monthlySolve;
    }
}
