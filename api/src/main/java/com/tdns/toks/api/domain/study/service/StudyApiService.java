package com.tdns.toks.api.domain.study.service;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudiesInfoResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.api.domain.study.model.mapper.StudyApiMapper;
import com.tdns.toks.core.domain.quiz.model.dto.QuizDTO;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.study.model.dto.StudyDTO.InProgressStudyInfoLight;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.study.model.dto.TagDTO;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyUser;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.study.service.TagService;
import com.tdns.toks.core.domain.study.type.StudyCapacity;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import com.tdns.toks.core.domain.study.type.StudyUserStatus;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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


    public void joinStudy(long studyId) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.getStudy(studyId);
        joinStudy(study, userDTO.getId());
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

    public StudiesInfoResponse getStudies() {
        var userDTO = UserDetailDTO.get();
        var userStudies = userService.getUserStudies(userDTO.getId());
        return new StudiesInfoResponse(userStudies.stream().map(study -> {
            QuizDTO.LatestQuizSimpleDto latestQuizStatus = quizService.getLatestQuizStatus(study.getId(), userDTO.getId());
            List<TagDTO> tags = tagService.getStudyTagsDTO(study.getId());
            return InProgressStudyInfoLight.toDto(study, latestQuizStatus, tags);
        }).collect(Collectors.toList()));
    }
}
