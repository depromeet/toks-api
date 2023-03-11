package com.tdns.toks.api.domain.study.service;

import com.tdns.toks.api.domain.study.event.publish.TagDictionaryEventPublish;
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
import com.tdns.toks.core.domain.study.service.StudyTagService;
import com.tdns.toks.core.domain.study.service.StudyUserService;
import com.tdns.toks.core.domain.study.type.StudyCapacity;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import com.tdns.toks.core.domain.study.type.StudyUserStatus;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.FinishedStudiesInfoResponse;
import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudiesInfoResponse;
import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyDetailsResponse;
import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyEntranceDetailsResponse;
import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import static com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.TagResponse;

// TODO : 단건 조회시, 한번에 쿼리가 조회되도록 구현
// TODO : 개선 작업 필요 -> 스터디의 상태를 쿼리파람으로 받아서 하도록 구현해야 함.. 현재 로직은 잘못된 로직 (상태에 따라 API를 모두 만드는 건 바람직하지 않음)
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudyApiService {
    private final TagDictionaryEventPublish tagDictionaryEventPublish;
    private final StudyService studyService;
    private final UserService userService;
    private final StudyTagService tagService;
    private final StudyApiMapper mapper;
    private final QuizService quizService;
    private final StudyUserService studyUserService;

    public StudyApiResponse createStudy(StudyCreateRequest studyCreateRequest) {
        var userDTO = UserDetailDTO.get();
        var tagNames = studyCreateRequest.getTags().stream().map(
                t -> t.replaceAll(" ", "")
        ).collect(Collectors.toList());

        var study = studyService.save(mapper.toEntity(studyCreateRequest, userDTO.getId(), tagNames));

        joinStudy(study, userDTO.getId());

        log.info("create study uid : {} / studyId : {}", userDTO.getId(), study.getId());

        tagDictionaryEventPublish.publish(study.getId(), tagNames);

        var tagDto = tagNames
                .stream()
                .map(t -> TagDTO.of(null, t))
                .collect(Collectors.toList());

        return StudyApiResponse.of(study, userDTO, tagDto);
    }

    public StudyFormResponse getFormData() {
        return new StudyFormResponse();
    }

    // TODO : 태그 저장시 발생하는 동시성 문제에 대해서, Mqueue 혹은 RedisonClient를 토유해 해결해야함
    @Transactional(readOnly = true)
    public TagResponse getTagByKeyword(String keyword) {
        var nonBlankKeyword = keyword.replaceAll(" ", "");
        var tagDTOList = studyService.getTagListByKeyword(nonBlankKeyword)
                .stream()
                .map(TagDTO::of)
                .collect(Collectors.toList());

        return TagResponse.of(tagDTOList);
    }


    public void joinStudy(long studyId) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.getStudy(studyId);
        joinStudy(study, userDTO.getId());

        log.info("join study / uid : {} / studyId : {}", userDTO.getId(), studyId);
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
        var usersStudyIds = userStudies.stream()
                .map(StudyUser::getStudyId)
                .collect(Collectors.toList());

        var studies = studyService.findStudyAll(usersStudyIds)
                .stream()
                .map(aa -> getStudyInfo(aa, userId))
                .collect(Collectors.toList());

        return new StudiesInfoResponse(studies);
    }

    public StudiesInfoResponse getInProgressStudies() {
        var userId = UserDetailDTO.get().getId();
        var studyUsers = userService.getUserStudyIds(userId);
        var usersStudyIds = studyUsers.stream()
                .map(StudyUser::getStudyId)
                .collect(Collectors.toList());

        var studies = studyService.findStudyAll(usersStudyIds)
                .stream()
                .filter(s -> s.getStatus() != StudyStatus.FINISH)
                .map(aa -> getStudyInfo(aa, userId))
                .collect(Collectors.toList());

        return new StudiesInfoResponse(studies);
    }

    // 1차 개선 -> 태그 관련 정보를 레디스로
    public StudyInfoLight getStudyInfo(Study study, Long uid) {
        var tags = tagService.getStudyTagsDTO(study.getId());
        var studyLatestQuiz = quizService.getStudyLatestQuiz(study.getId());
        if (studyLatestQuiz.getId() == -1) {
            return StudyInfoLight.toDto(study, new QuizDTO.LatestQuizSimpleDto(StudyLatestQuizStatus.PENDING, -1L), tags);
        }
        var latestQuizSimpleDto = new QuizDTO.LatestQuizSimpleDto(
                quizService.getStudyLatestQuizStatus(studyLatestQuiz, uid),
                studyLatestQuiz.getId()
        );
        return StudyInfoLight.toDto(study, latestQuizSimpleDto, tags);
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

    // TODO : 스터디 조회시, 전체, 진행, 종료 등에 대한 상태값을 Param 기반으로 조회 진행
    // TODO : 스터디 조회시 비동기 멀티 스레드 기반으로 조회
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

        var usersStudyIds = userFinishedStudies.stream()
                .map(StudyUser::getStudyId)
                .collect(Collectors.toList());

        var response = studyService.findStudyAll(usersStudyIds)
                .stream()
                .filter(finishedStudy -> finishedStudy.getStatus() == StudyStatus.FINISH)
                .map(finishedStudy -> {
                    var tags = tagService.getStudyTagsDTO(finishedStudy.getId());
                    return StudyDTO.FinishedStudyInfoLight.toDto(finishedStudy, tags);
                }).collect(Collectors.toList());

        return new FinishedStudiesInfoResponse(response);
    }

    public Long deleteAllByLeaderId(Long leaderId) {
        return studyService.deleteAllByLeaderId(leaderId);
    }

    public void leaveStudy(Long studyId) {
        var userId = UserDetailDTO.get().getId();
        studyUserService.leaveStudy(studyId, userId);
    }
}
