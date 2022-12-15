package com.tdns.toks.api.domain.study.service;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.api.domain.study.model.mapper.StudyApiMapper;
import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final StudyApiMapper mapper;

    public StudyApiResponse createStudy(StudyCreateRequest studyCreateRequest) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.save(mapper.toEntity(studyCreateRequest, userDTO.getId()));

        List<Tag> tagList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(studyCreateRequest.getTagList())) {
            tagList = studyService.getOrCreateTagListByKeywordList(studyCreateRequest.getTagList());
            studyService.saveAllStudyTag(mapper.toEntity(tagList, study.getId()));
        }
        return StudyApiResponse.toResponse(study, userDTO, tagList);
    }

    public StudyFormResponse getFormData() {
        return new StudyFormResponse();
    }

    @Transactional(readOnly = true)
    public TagResponse getTagByKeyword(String keyword) {
        var tagDTOList = studyService.getTagListByKeyword(keyword.trim()).stream().map(tag -> TagDTO.of(tag)).collect(Collectors.toList());
        return TagResponse.of(tagDTOList);
    }
}
