package com.tdns.toks.api.domain.study.event.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class TagsEventModel {
    private final Long studyId;
    private final List<String> tags;
}
