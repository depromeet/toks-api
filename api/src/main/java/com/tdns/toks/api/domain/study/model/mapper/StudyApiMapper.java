package com.tdns.toks.api.domain.study.model.mapper;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudyApiMapper {
    private static Integer INIT_STUDY_USER_COUNT = 0;
    private static Integer INIT_STUDY_QUIZ_ROUND = 0;

    public Study toEntity(StudyApiDTO.StudyCreateRequest studyCreateRequest, Long userId) {
        var studyStatus = StudyStatus.getStatus(
                studyCreateRequest.getStartedAt(),
                studyCreateRequest.getEndedAt(),
                LocalDateTime.now()
        );

        var studyDescription = "";
        if (studyCreateRequest.getDescription() != null) {
            studyDescription = studyCreateRequest.getDescription();
        }

        return Study.builder()
                .name(studyCreateRequest.getName())
                .description(studyDescription)
                .startedAt(studyCreateRequest.getStartedAt())
                .endedAt(studyCreateRequest.getEndedAt())
                .status(studyStatus)
                .capacity(studyCreateRequest.getCapacity())
                .studyUserCount(INIT_STUDY_USER_COUNT)
                .leaderId(userId)
                .latestQuizRound(INIT_STUDY_QUIZ_ROUND)
                .build();
    }

    public List<StudyTag> toEntity(List<Tag> tags, Long studyId) {
        return tags.stream()
                .map(tag -> toEntity(tag.getId(), studyId))
                .collect(Collectors.toList());
    }

    public StudyTag toEntity(long tagId, long studyId) {
        return StudyTag.builder()
                .studyId(studyId)
                .tagId(tagId)
                .build();
    }

    public Tag toEntity(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }
}
