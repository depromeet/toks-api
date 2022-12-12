package com.tdns.toks.core.domain.study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class StudyTagsDTO {

    @Getter
    @AllArgsConstructor
    public static class StudyTagsDto{
        private List<TagDTO> studyTagsDto;
    }
}
