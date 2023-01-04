package com.tdns.toks.core.domain.study.type;

import java.time.Duration;
import java.time.LocalDateTime;

public enum StudyProgress {
    STEP0(0), STEP1(16), STEP2(33), STEP3(50), STEP4(66), STEP5(83);

    public final int value;

    StudyProgress(int value) {
        this.value = value;
    }

    private int getValue() {
        return this.value;
    }

    public static StudyProgress getProgress(LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime now) {
        long total = Duration.between(startedAt, endedAt).getSeconds();
        long onGoing = Duration.between(startedAt, now).getSeconds();
        if (onGoing < 0) {
            return STEP0;
        }
        int progress = (int)((onGoing * 100.0) / total + 0.5);
        StudyProgress[] studyProgresses = StudyProgress.values();
        for (int i = 0; i < studyProgresses.length-1; i++) {
            if (studyProgresses[i].getValue() <= progress && progress < studyProgresses[i + 1].getValue()) {
                return studyProgresses[i];
            }
        }
        return STEP5;
    }
}
