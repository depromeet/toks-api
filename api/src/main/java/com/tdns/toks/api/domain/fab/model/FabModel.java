package com.tdns.toks.api.domain.fab.model;

import com.tdns.toks.core.domain.user.entity.UserActivityCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FabModel {
    private long userId;
    private String userName;
    private int totalVisitCount;
    private int todaySolveCount;
    private int totalSolveCount;
    private List<DailySolveCountModel> monthlySolveCount;

    public static FabModel of(
            UserActivityCount userActivityCount,
            List<DailySolveCountModel> userMonthlySolveActivity,
            Integer todaySolveCount
    ) {
        return FabModel.builder()
                .userId(userActivityCount.getUserId())
                .totalVisitCount(userActivityCount.getTotalVisitCount())
                .totalSolveCount(userActivityCount.getTotalSolveCount())
                .todaySolveCount(todaySolveCount)
                .monthlySolveCount(userMonthlySolveActivity)
                .build();
    }
}
