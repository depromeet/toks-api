package com.tdns.toks.api.domain.fab.model;

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
    private List<Integer> monthlySolveCount;
}
