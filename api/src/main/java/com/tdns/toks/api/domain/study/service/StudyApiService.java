package com.tdns.toks.api.domain.study.service;

import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.study.model.mapper.StudyApiMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.model.dto.QuizDTO;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.quiz.type.StudyLatestQuizStatus;
import com.tdns.toks.core.domain.study.model.dto.StudyDTO;
import com.tdns.toks.core.domain.study.model.dto.StudyDTO.StudyInfoLight;
import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyUser;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.study.service.TagService;
import com.tdns.toks.core.domain.study.type.StudyCapacity;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import com.tdns.toks.core.domain.study.type.StudyUserStatus;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;
import com.tdns.toks.core.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudyApiService {
    private final StudyService studyService;
    private final UserService userService;
    private final TagService tagService;
    private final StudyApiMapper mapper;
    private final QuizService quizService;

    public StudyApiResponse createStudy(StudyCreateRequest studyCreateRequest) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.save(mapper.toEntity(studyCreateRequest, userDTO.getId()));

        var tags = studyService.getOrCreateTagListByKeywordList(studyCreateRequest.getTags());
        studyService.saveAllStudyTag(mapper.toEntity(tags, study.getId()));

        joinStudy(study, userDTO.getId());

        log.info("create study uid : {} / studyId : {}", userDTO.getId(), study.getId());
        return StudyApiResponse.toResponse(study, userDTO, tags);
    }

    public StudyFormResponse getFormData() {
        return new StudyFormResponse();
    }

    @Transactional(readOnly = true)
    public TagResponse getTagByKeyword(String keyword) {
        var tagDTOList = studyService.getTagListByKeyword(keyword.trim())
                .stream()
                .map(TagDTO::of)
                .collect(Collectors.toList());

        return TagResponse.of(tagDTOList);
    }


    public void joinStudy(long studyId) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.getStudy(studyId);
        joinStudy(study, userDTO.getId());
    }

    private Study convertToEntity(StudyCreateRequest studyCreateRequest, Long userId) {
        return Study.builder()
                .name(studyCreateRequest.getName())
                .description(studyCreateRequest.getDescription())
                .startedAt(studyCreateRequest.getStartedAt())
                .endedAt(studyCreateRequest.getEndedAt())
                .status(StudyStatus.getStatus(studyCreateRequest.getStartedAt(), studyCreateRequest.getEndedAt(), LocalDateTime.now()))
                .capacity(studyCreateRequest.getCapacity())
                .studyUserCount(0)
                .leaderId(userId)
                .build();
    }

    private StudyUser convertToEntity(long userId, long studyId) {
        return StudyUser.builder()
                .userId(userId)
                .studyId(studyId)
                .status(StudyUserStatus.ACTIVE) //1차 MVP 때는 승인&수락 기능이 빠지기 때문에 ACTIVE, 추후 생성 시에는 REQUEST로 변경 예정
                .build();
    }

    private StudyUser joinStudy(Study study, long userId) {
        {
            if (StudyStatus.FINISH.equals(study.getStatus())) {
                throw new SilentApplicationErrorException(ApplicationErrorType.ALREADY_FINISH_STUDY);
            }
            if (StudyCapacity.headCountIsOver(study.getCapacity(), study.getStudyUserCount())) {
                throw new SilentApplicationErrorException(ApplicationErrorType.OVER_MAX_HEADCOUNT);
            }
            if (studyService.existStudyUser(userId, study.getId())) {
                throw new SilentApplicationErrorException(ApplicationErrorType.ALREADY_JOIN_USER);
            }
            var studyUser = studyService.saveStudyUser(convertToEntity(userId, study.getId()));
            study.updateStudyUserCount(1);
            return studyUser;
        }
    }

    public StudiesInfoResponse getAllStudies() {
        var userId = UserDetailDTO.get().getId();
        var userStudies = userService.getUserStudyIds(userId);
        var response = userStudies.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return new StudiesInfoResponse(response);
    }

    public StudiesInfoResponse getInProgressStudies() {
        var userId = UserDetailDTO.get().getId();
        var userStudies = userService.getUserStudyIds(userId);
        return new StudiesInfoResponse(userStudies.stream()
                .filter(studyUser -> !studyService.isFinishedStudy(studyUser.getStudyId()))
                .map(this::convertToResponse)
                .collect(Collectors.toList()));
    }

    public StudyInfoLight convertToResponse(StudyUser studyUser) {
        var studyId = studyUser.getStudyId();
        var study = studyService.getStudy(studyId);
        var tags = tagService.getStudyTagsDTO(studyId);
        var studyLatestQuiz = quizService.getStudyLatestQuiz(studyId);
        if (studyLatestQuiz.getId() == -1) {
            return StudyInfoLight.toDto(study, new QuizDTO.LatestQuizSimpleDto(StudyLatestQuizStatus.PENDING, -1L), tags);
        }
        var latestQuizSimpleDto = new QuizDTO.LatestQuizSimpleDto(
                quizService.getStudyLatestQuizStatus(studyLatestQuiz, studyUser.getUserId()),
                studyLatestQuiz.getId()
        );
        return StudyInfoLight.toDto(study, latestQuizSimpleDto, tags);
    }

    public StudyDetailsResponse getStudyDetails(Long studyId) {
        var users = studyService.getUsersInStudy(studyId).stream()
                .map(UserSimpleDTO::toDto)
                .collect(Collectors.toList());
        var tags = tagService.getStudyTagsDTO(studyId);
        var study = studyService.getStudy(studyId);

        return StudyDetailsResponse.toResponse(study, users, tags);
    }

    public StudyEntranceDetailsResponse getStudyEntranceDetails(Long studyId) {
        var tags = tagService.getStudyTagsDTO(studyId);
        var study = studyService.getStudy(studyId);

        return StudyEntranceDetailsResponse.toResponse(study, tags);
    }

    public FinishedStudiesInfoResponse getFinishedStudies() {
        var userId = UserDetailDTO.get().getId();
        var userFinishedStudies = userService.getUserStudyIds(userId);

        return new FinishedStudiesInfoResponse(userFinishedStudies.stream()
                .filter(finishedStudy -> studyService.isFinishedStudy(finishedStudy.getStudyId()))
                .map(finishedStudy -> {
                    var studyId = finishedStudy.getStudyId();
                    var study = studyService.getStudy(studyId);
                    var tags = tagService.getStudyTagsDTO(studyId);
                    return StudyDTO.FinishedStudyInfoLight.toDto(study, tags);
                }).collect(Collectors.toList()));
    }

    public Long deleteAllByLeaderId(Long leaderId) {
        return studyService.deleteAllByLeaderId(leaderId);
    }
}
