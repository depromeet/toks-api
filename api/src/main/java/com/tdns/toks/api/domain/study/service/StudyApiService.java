package com.tdns.toks.api.domain.study.service;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudiesInfoResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.api.domain.study.model.mapper.StudyApiMapper;
import com.tdns.toks.core.domain.study.model.dto.StudyDTO.InProgressStudyInfoLight;
import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.service.UserService;
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
    private final UserService userService;
    private final StudyApiMapper mapper;

    public StudyApiResponse createStudy(StudyCreateRequest studyCreateRequest) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.save(mapper.toEntity(studyCreateRequest, userDTO.getId()));

        List<Tag> tagList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(studyCreateRequest.getTagIdList())) {
            tagList = studyService.getTagListByIdList(studyCreateRequest.getTagIdList());
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


    public TagDTO getOrCreateKeyword(TagCreateRequest tagCreateRequest) {
        var keyword = tagCreateRequest.getKeyword().trim();
        var tag = Optional.ofNullable(studyService.getTagByKeyword(keyword))
                .orElseGet(() -> studyService.createTag(mapper.toEntity(tagCreateRequest.getKeyword())));
        return TagDTO.of(tag);
    }

    public StudiesInfoResponse getStudies() {
        var userDTO = UserDetailDTO.get();
        var userStudies = userService.getUserStudies(userDTO.getId());
        return toStudyDTOs(userStudies);
    }

    private StudiesInfoResponse toStudyDTOs(List<Study> studies) {
        List<InProgressStudyInfoLight> output = new ArrayList<>();
        for (Study study : studies) {
            //  todo 해당 스터디의 latestQuiz 가져오기
            output.add(InProgressStudyInfoLight.builder()
                    .id(study.getId())
                    .name(study.getName())
//                            .latestQuizStatus()
//                            .latestQuizId()
                    .studyUserCount(study.getStudyUserCount())
                    .studyTags(getStudyTagsDTO(study.getId()))
                    .build());
        }
        return new StudiesInfoResponse(output);
    }

    private List<TagDTO> getStudyTagsDTO(Long studyId) {
        return studyService.getStudyTags(studyId).stream().map(tag -> TagDTO.of(tag)).collect(Collectors.toList());
    }
}
