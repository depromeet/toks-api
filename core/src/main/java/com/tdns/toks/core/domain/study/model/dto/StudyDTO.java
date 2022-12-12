package com.tdns.toks.core.domain.study.model.dto;

import com.tdns.toks.core.domain.quiz.type.LatestQuizStatus;
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
        private Integer studyUserCount;
        private Long latestQuizId;
        private LatestQuizStatus latestQuizStatus;
        private List<TagDTO> studyTags;
    }
}
