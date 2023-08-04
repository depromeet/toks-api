package com.tdns.toks.api.domain.fab.model.dto;

import com.tdns.toks.api.domain.fab.model.FabModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FabDto {
    @Getter
    @NoArgsConstructor
    public static class GetFabResponseDto {
        private String title;
        private String description1;
        private String description2;
        private List<Integer> calendar;

        public GetFabResponseDto(FabModel fabModel) {
            this.title = fabModel.getUserName() + "님 " + fabModel.getTotalVisitCount() + "번째 출석 성공!";
            this.description1 = "오늘은 " + fabModel.getTodaySolveCount() + "개의 퀴즈를 풀었어요.";
            this.description2 = "총 " + fabModel.getTotalSolveCount() + "개의 퀴즈를 풀었어요.";
            this.calendar = fabModel.getMonthlySolveCount();
        }
    }
}
