package com.tdns.toks.core.domain.study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StudyTagsDTO {
    private List<TagDTO> studyTags;
}
