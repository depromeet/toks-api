package com.tdns.toks.api.domain.fab.service;

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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FabService {

    private final UserActivityCountRepository userActivityCountRepository;
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;


    public FabDto.GetFabResponseDto getFabInfo(AuthUser authUser, int month, int year) {
        UserActivityCount userActivityCount = userActivityCountRepository.findByUserId(authUser.getId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER_ACTIVITY));

        List<UserDailySolveCountModel> userMonthlySolveActivity = quizReplyHistoryRepository.findUserMonthlySolveActivity(userActivityCount.getUserId(), month, year);

        int[] monthlySolve = new int[31];
        for (UserDailySolveCountModel model : userMonthlySolveActivity) {
            int date = Integer.parseInt(model.getDate().split("-")[2]);
            int count = model.getValue();
            monthlySolve[date-1] = count;
        }
        int today = LocalDate.now().getDayOfMonth();

        FabModel fabModel = FabModel.builder()
                .userId(userActivityCount.getUserId())
                .totalVisitCount(userActivityCount.getTotalVisitCount())
                .totalSolveCount(userActivityCount.getTotalSolveCount())
                .todaySolveCount(monthlySolve[today-1])
                .monthlySolveCount(Arrays.stream(monthlySolve).boxed().collect(Collectors.toList()))
                .build();
        return new FabDto.GetFabResponseDto(fabModel);
    }
}
