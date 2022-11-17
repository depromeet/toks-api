package com.tdns.toks.api.domain.study.service;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import com.tdns.toks.core.domain.user.model.dto.UserDTO;
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
        return StudyApiResponse.toResponse(study, userDTO);
    }

    public StudyFormResponse getFormData() {
        return new StudyFormResponse();
    }

    private Study convertToEntity(StudyCreateRequest studyCreateRequest, Long userId){
        return Study.builder()
                .name(studyCreateRequest.getName())
                .description(studyCreateRequest.getDescription())
                .startDate(studyCreateRequest.getStartDate())
                .endDate(studyCreateRequest.getEndDate())
                .status(StudyStatus.getStatus(studyCreateRequest.getStartDate(), studyCreateRequest.getEndDate(), LocalDate.now()))
                .capacity(studyCreateRequest.getCapacity())
                .leaderId(userId)
                .build();
    }
}
