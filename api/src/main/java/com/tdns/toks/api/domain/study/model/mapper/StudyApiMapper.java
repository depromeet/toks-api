package com.tdns.toks.api.domain.study.model.mapper;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudyApiMapper {
    public Study toEntity(StudyApiDTO.StudyCreateRequest studyCreateRequest, Long userId) {
        return Study.builder()
                .name(studyCreateRequest.getName())
                .description(studyCreateRequest.getDescription())
                .startDate(studyCreateRequest.getStartDate())
                .endDate(studyCreateRequest.getEndDate())
                .status(StudyStatus.getStatus(studyCreateRequest.getStartDate(), studyCreateRequest.getEndDate(), LocalDate.now()))
                .capacity(studyCreateRequest.getCapacity())
                .studyUserCount(0)
                .leaderId(userId)
                .build();
    }

    public List<StudyTag> toEntity(List<Tag> tagList, Long studyId) {
        return tagList.stream().map(tag -> toEntity(tag.getId(), studyId)).collect(Collectors.toList());
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