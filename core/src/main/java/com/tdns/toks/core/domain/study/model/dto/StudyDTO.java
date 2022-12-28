package com.tdns.toks.core.domain.study.model.dto;

import com.tdns.toks.core.domain.quiz.model.dto.QuizDTO.LatestQuizSimpleDto;
import com.tdns.toks.core.domain.quiz.type.StudyLatestQuizStatus;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class StudyDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class StudyInfoLight {
        private Long id;
        private String name;
        private Integer userCount;
        private StudyStatus studyStatus;
        private Long latestQuizId;
        private StudyLatestQuizStatus latestQuizStatus;
        private List<TagDTO> studyTags;

        public static StudyInfoLight toDto(Study study, LatestQuizSimpleDto latestQuizSimpleDto, List<TagDTO> tags) {
            return StudyInfoLight.builder()
                    .id(study.getId())
                    .name(study.getName())
                    .studyStatus(study.getStatus())
                    .latestQuizId(latestQuizSimpleDto.getQuizId())
                    .latestQuizStatus(latestQuizSimpleDto.getStudyLatestQuizStatus())
                    .userCount(study.getStudyUserCount())
                    .studyTags(tags)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class FinishedStudyInfoLight {
        private Long id;
        private String name;
        private Integer userCount;
        private List<TagDTO> studyTags;

        public static FinishedStudyInfoLight toDto(Study study, List<TagDTO> tags) {
            return FinishedStudyInfoLight.builder()
                    .id(study.getId())
                    .name(study.getName())
                    .userCount(study.getStudyUserCount())
                    .studyTags(tags)
                    .build();
        }
    }
}
