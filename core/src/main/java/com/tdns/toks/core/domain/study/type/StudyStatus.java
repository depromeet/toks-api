package com.tdns.toks.core.domain.study.type;

import com.tdns.toks.core.common.model.entity.EnumModel;

import java.time.LocalDate;

public enum StudyStatus implements EnumModel{
    READY("진행예정"), IN_PROGRESS("진행중"), FINISH("완료");

    public final String value;

    StudyStatus(String value) {
        this.value = value;
    }

    public static StudyStatus getStatus(LocalDate startDate, LocalDate endDate, LocalDate now) {
        if (now.isBefore(startDate)) {
            return READY;
        } else if (endDate.isBefore(now)) {
            return IN_PROGRESS;
        } else {
            return FINISH;
        }
    }

    @Override
    public String getKey() {
        return this.name();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
