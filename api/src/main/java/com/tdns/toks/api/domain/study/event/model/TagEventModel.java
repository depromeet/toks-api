package com.tdns.toks.api.domain.study.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagEventModel {
    private String tagName;
    private Long studyId;
}
