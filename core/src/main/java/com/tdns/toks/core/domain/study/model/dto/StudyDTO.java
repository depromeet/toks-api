package com.tdns.toks.core.domain.study.model.dto;

import com.tdns.toks.core.domain.quiz.type.StudyLatestQuizStatus;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

public class StudyDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class InProgressStudyInfoLight{
        private Long id;
        private String name;
        private Integer userCount;
        private Long latestQuizId;
        private StudyLatestQuizStatus latestQuizStatus;
        private List<TagDTO> studyTags;
    }
}
