package com.tdns.toks.api.domain.fab.model;

import com.tdns.toks.core.domain.user.model.UserDailySolveCountModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailySolveCountModel {
    private String date;
    private int count;

    public static DailySolveCountModel from(UserDailySolveCountModel model) {
        return new DailySolveCountModel(model.getDate(), model.getValue());
    }
}
