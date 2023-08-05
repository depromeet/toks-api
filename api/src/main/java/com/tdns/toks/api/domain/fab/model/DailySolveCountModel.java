package com.tdns.toks.api.domain.fab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailySolveCountModel {
    private String date;
    private int count;
}
