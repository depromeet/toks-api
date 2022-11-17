package com.tdns.toks.api.domain.study.service;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyUser;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import com.tdns.toks.core.domain.study.type.StudyUserStatus;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyApiService {
    private final StudyService studyService;

    public StudyApiResponse createStudy(StudyCreateRequest studyCreateRequest) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.save(convertToEntity(studyCreateRequest, userDTO.getId()));
        joinStudy(study, userDTO.getId());
        return StudyApiResponse.toResponse(study, userDTO);
    }

    public StudyFormResponse getFormData() {
        return new StudyFormResponse();
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
            } else if (study.getCapacity().maxHeadCount != null && study.getCapacity().maxHeadCount <= study.getStudyUserCount()) {
                throw new SilentApplicationErrorException(ApplicationErrorType.OVER_MAX_HEADCOUNT);
            } else if (studyService.existStudyUser(userId, study.getId())) {
                throw new SilentApplicationErrorException(ApplicationErrorType.ALREADY_JOIN_USER);
            }
            var studyUser = studyService.saveStudyUser(convertToEntity(userId, study.getId()));
            study.updateStudyUserCount(1);
            return studyUser;
        }
    }
}
