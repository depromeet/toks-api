package com.tdns.toks.core.domain.study.type;

import com.tdns.toks.core.common.model.entity.EnumModel;

public enum StudyCapacity implements EnumModel {
    SMALL("2-4명"), MEDIUM("5-7명"), LARGE("8명 이상");

    public final String value;

    StudyCapacity(String value) {
        this.value = value;
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
