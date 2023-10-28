package com.tdns.toks.api.domain.fab.model.dto;

import com.tdns.toks.api.domain.fab.model.DailySolveCountModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FabDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetFabUserDataResponseDto {
        private String username;
        private Integer attendance;
        private String description1;
        private String description2;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetFabCalendarDataResponseDto {
        private List<DailySolveCountModel> calendar;
    }
}
