package com.tdns.toks.core.domain.study.type;

import java.time.LocalDate;

public enum StudyStatus {
    READY, IN_PROGRESS, FINISH;

    public static StudyStatus getStatus(LocalDate startDate, LocalDate endDate, LocalDate now) {
        if (now.isBefore(startDate)) {
            return READY;
        } else if (endDate.isBefore(now)) {
            return IN_PROGRESS;
        } else {
            return FINISH;
        }
    }
}
