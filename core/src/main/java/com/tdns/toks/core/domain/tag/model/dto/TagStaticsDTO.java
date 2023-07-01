package com.tdns.toks.core.domain.tag.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TagStaticsDTO {
    private final Long allCount;
    private final Long dailyCount;
}
