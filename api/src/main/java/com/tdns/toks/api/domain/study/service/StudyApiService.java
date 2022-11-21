package com.tdns.toks.api.domain.study.service;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.TagCreateRequest;
import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.TagResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyApiService {
    private final StudyService studyService;

    public StudyApiResponse createStudy(StudyCreateRequest studyCreateRequest) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.save(convertToEntity(studyCreateRequest, userDTO.getId()));

        List<Tag> tagList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(studyCreateRequest.getTagIdList())) {
            tagList = studyService.getTagListByIdList(studyCreateRequest.getTagIdList());
            studyService.saveAllStudyTag(convertToEntityList(tagList, study.getId()));
        }
        return StudyApiResponse.toResponse(study, userDTO, tagList);
    }

    public StudyFormResponse getFormData() {
        return new StudyFormResponse();
    }

    @Transactional(readOnly = true)
    public TagResponse getTagByKeyword(String keyword) {
        var tagDTOList = studyService.getTagListByKeyword(keyword).stream().map(tag -> TagDTO.of(tag)).collect(Collectors.toList());
        return TagResponse.of(tagDTOList);
    }


    @Transactional
    public TagDTO getOrCreateKeyword(TagCreateRequest tagCreateRequest) {
        var tag = Optional.ofNullable(studyService.getTagByKeyword(tagCreateRequest.getKeyword()))
                .orElseGet(() -> studyService.createTag(convertToEntity(tagCreateRequest.getKeyword())));
        return TagDTO.of(tag);
    }

    private Study convertToEntity(StudyCreateRequest studyCreateRequest, Long userId) {
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

    private List<StudyTag> convertToEntityList(List<Tag> tagList, Long studyId) {
        return tagList.stream().map(tag -> convertToEntity(tag.getId(), studyId)).collect(Collectors.toList());
    }

    private StudyTag convertToEntity(long tagId, long studyId) {
        return StudyTag.builder()
                .studyId(studyId)
                .tagId(tagId)
                .build();
    }

    private Tag convertToEntity(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }
}
