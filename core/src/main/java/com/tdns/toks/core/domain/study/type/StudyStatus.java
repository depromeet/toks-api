package com.tdns.toks.core.domain.study.type;

import com.tdns.toks.core.common.model.entity.EnumModel;

import java.time.LocalDateTime;

public enum StudyStatus implements EnumModel{
    READY("진행예정"), IN_PROGRESS("진행중"), FINISH("완료");

    public final String value;

    StudyStatus(String value) {
        this.value = value;
    }

    public static StudyStatus getStatus(LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime now) {
        if (now.isBefore(startedAt)) {
            return READY;
        } else if (now.isAfter(endedAt)) {
            return FINISH;
        } else {
            return IN_PROGRESS;
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
