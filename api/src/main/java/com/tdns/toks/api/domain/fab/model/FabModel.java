package com.tdns.toks.api.domain.fab.model;

import com.tdns.toks.api.domain.user.model.UserModel;
import com.tdns.toks.core.domain.user.entity.UserActivityCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FabModel {
    private Long userId;
    private String userName;
    private Integer totalVisitCount;
    private Integer todaySolveCount;
    private Integer totalSolveCount;
    private List<DailySolveCountModel> monthlySolveCount;

    public static FabModel of(
            UserActivityCount userActivityCount,
            List<DailySolveCountModel> userMonthlySolveActivity,
            UserModel userModel,
            Integer todaySolveCount
    ) {
        return FabModel.builder()
                .userId(userActivityCount.getUserId())
                .userName(userModel.getNickname())
                .totalVisitCount(userActivityCount.getTotalVisitCount())
                .totalSolveCount(userActivityCount.getTotalSolveCount())
                .todaySolveCount(todaySolveCount)
                .monthlySolveCount(userMonthlySolveActivity)
                .build();
    }

    public FabModel calenderDataOnly(List<DailySolveCountModel> userMonthlySolveActivity){
        return new FabModel(null, null, null, null, null, userMonthlySolveActivity);
    }

    public FabModel userDataOnly(UserActivityCount userActivityCount, UserModel userModel, Integer todaySolveCount) {
        return new FabModel(userActivityCount.getUserId(),userModel.getNickname(),userActivityCount.getTotalVisitCount(),userActivityCount.getTotalSolveCount(),todaySolveCount, null);
    }
}
