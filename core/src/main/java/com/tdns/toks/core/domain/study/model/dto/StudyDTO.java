package com.tdns.toks.core.domain.study.model.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

public class StudyDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class InProgressStudyInfoLight{
        private Long id;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer studyUserCount;
        private StudyTagsDTO studyTagsDTO;
    }
}
