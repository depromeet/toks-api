package com.tdns.toks.core.domain.study.model.dto;

import com.tdns.toks.core.domain.quiz.model.dto.QuizDTO.LatestQuizSimpleDto;
import com.tdns.toks.core.domain.quiz.type.StudyLatestQuizStatus;
import com.tdns.toks.core.domain.study.model.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

        public static InProgressStudyInfoLight toDto(Study study, LatestQuizSimpleDto latestQuizSimpleDto, List<TagDTO> tags) {
            return InProgressStudyInfoLight.builder()
                    .id(study.getId())
                    .name(study.getName())
                    .latestQuizId(latestQuizSimpleDto.getQuizId())
                    .latestQuizStatus(latestQuizSimpleDto.getStudyLatestQuizStatus())
                    .userCount(study.getStudyUserCount())
                    .studyTags(tags)
                    .build();
        }
    }
}