package com.tdns.toks.api.domain.study.service;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyDetailsResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudiesInfoResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.api.domain.study.model.mapper.StudyApiMapper;
import com.tdns.toks.core.domain.quiz.model.dto.QuizDTO.LatestQuizSimpleDto;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.study.model.dto.StudyDTO.InProgressStudyInfoLight;
import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;
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
    private final QuizService quizService;

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
        return toStudyDTOs(userStudies, userDTO.getId());
    }

    private StudiesInfoResponse toStudyDTOs(List<Study> studies, Long userId) {

        List<InProgressStudyInfoLight> output = studies.stream().map(study -> InProgressStudyInfoLight.builder()
                .id(study.getId())
                .name(study.getName())
                //todo 근데 이거 getStudyLastestQuizInfo 2번 호출하는거 너무 빡치지만 일단 map으로 변환 -? 방법이 있을까요?
                .latestQuizId(getStudyLatestQuizInfo(study.getId(), userId).getQuizId())
                .latestQuizStatus(getStudyLatestQuizInfo(study.getId(), userId).getStudyLatestQuizStatus())
                .userCount(study.getStudyUserCount())
                .studyTags(getStudyTagsDTO(study.getId()))
                .build()).collect(Collectors.toList());
        return new StudiesInfoResponse(output);
    }

    private LatestQuizSimpleDto getStudyLatestQuizInfo(Long studyId, Long userId) {
        Optional<Quiz> latestQuiz = studyService.getLatestQuiz(studyId);
        return quizService.getLatestQuizStatus(latestQuiz, userId);
    }

    private List<TagDTO> getStudyTagsDTO(Long studyId) {
        return studyService.getStudyTags(studyId).stream()
                .map(tag -> TagDTO.of(tag)).collect(Collectors.toList());
    }

    public StudyDetailsResponse getStudyDetails(Long studyId) {
        var users = studyService.getUsersInStudy(studyId).stream()
                .map(user -> UserSimpleDTO.toDto(user)).collect(Collectors.toList());
        var tags = studyService.getStudyTags(studyId).stream()
                .map(tag -> TagDTO.of(tag)).collect(Collectors.toList());
        var study = studyService.getStudy(studyId);
        return StudyDetailsResponse.toResponse(study, users, tags);
    }
}
