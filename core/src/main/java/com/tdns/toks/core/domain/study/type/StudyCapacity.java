package com.tdns.toks.core.domain.study.type;

import com.tdns.toks.core.common.model.entity.EnumModel;

public enum StudyCapacity implements EnumModel {
    SMALL("2-4명", 4), MEDIUM("5-7명", 7), LARGE("8명 이상", 8);

    public final String value;
    public final int maxHeadCount;

    StudyCapacity(String value, int maxHeadCount) {
        this.value = value;
        this.maxHeadCount = maxHeadCount;
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
