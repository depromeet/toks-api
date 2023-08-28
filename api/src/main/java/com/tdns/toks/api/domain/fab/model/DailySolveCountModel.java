package com.tdns.toks.api.domain.fab.model;

import com.tdns.toks.core.domain.quiz.model.UserDailySolveCountModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailySolveCountModel {
    private String date;
    private Long count;

    public static DailySolveCountModel from(UserDailySolveCountModel model) {
        return new DailySolveCountModel(model.getDate(), model.getValue());
    }
}
