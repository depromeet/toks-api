package com.tdns.toks.api.domain.study.service;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudiesInfoResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.api.domain.study.model.mapper.StudyApiMapper;
import com.tdns.toks.core.domain.quiz.model.dto.QuizDTO;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.study.model.dto.StudyDTO.InProgressStudyInfoLight;
import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.study.service.TagService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.TagResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyApiService {
    private final StudyService studyService;
    private final UserService userService;
    private final QuizService quizService;
    private final TagService tagService;
    private final StudyApiMapper mapper;

    public StudyApiResponse createStudy(StudyCreateRequest studyCreateRequest) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.save(mapper.toEntity(studyCreateRequest, userDTO.getId()));

        List<Tag> tags = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(studyCreateRequest.getTagList())) {
            tags = studyService.getOrCreateTagListByKeywordList(studyCreateRequest.getTagList());
            studyService.saveAllStudyTag(mapper.toEntity(tags, study.getId()));
        }
        return StudyApiResponse.toResponse(study, userDTO, tags);
    }

    public StudyFormResponse getFormData() {
        return new StudyFormResponse();
    }

    @Transactional(readOnly = true)
    public TagResponse getTagByKeyword(String keyword) {
        var tagDTOList = studyService.getTagListByKeyword(keyword.trim()).stream().map(tag -> TagDTO.of(tag)).collect(Collectors.toList());
        return TagResponse.of(tagDTOList);
    }

    public StudiesInfoResponse getStudies() {
        var userDTO = UserDetailDTO.get();
        var userStudies = userService.getUserStudies(userDTO.getId());
        return new StudiesInfoResponse(userStudies.stream().map(study -> {
            QuizDTO.LatestQuizSimpleDto latestQuizStatus = quizService.getLatestQuizStatus(study.getId(), userDTO.getId());
            List<TagDTO> tagDTOS = tagService.getStudyTagsDTO(study.getId());
            return InProgressStudyInfoLight.builder()
                    .id(study.getId())
                    .name(study.getName())
                    .latestQuizId(latestQuizStatus.getQuizId())
                    .latestQuizStatus(latestQuizStatus.getStudyLatestQuizStatus())
                    .userCount(study.getStudyUserCount())
                    .studyTags(tagDTOS)
                    .build();
        }).collect(Collectors.toList()));
    }
}
