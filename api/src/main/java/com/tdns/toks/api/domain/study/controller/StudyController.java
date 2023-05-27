package com.tdns.toks.api.domain.study.controller;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudiesInfoResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyDetailsResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyEntranceDetailsResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.TagResponse;
import com.tdns.toks.api.domain.study.service.StudyApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import com.tdns.toks.core.domain.study.type.StudyUserStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

// TODO : 해당 클래스에 있는 조회 로직에 대해 개선 작업이 필요함
@Tag(name = "StudyController-V1", description = "STUDY API")
@RestController
@RequestMapping(path = "/api/v1/studies", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudyController {
    private final StudyApiService studyApiService;

    @PostMapping
    @Operation(summary = "스터디 생성")
    public ResponseEntity<StudyApiResponse> createStudy(
            @Validated @RequestBody StudyCreateRequest studyCreateRequest
    ) {
        var response = studyApiService.createStudy(studyCreateRequest);
        return ResponseDto.created(response);
    }

    @GetMapping("/form-data")
    @Operation(summary = "스터디 생성용 폼 데이터 조회")
    public ResponseEntity<StudyFormResponse> getFormData(
    ) {
        var response = studyApiService.getFormData();
        return ResponseDto.ok(response);
    }

    @PostMapping("/{studyId}/join")
    @Operation(summary = "스터디 가입")
    public ResponseEntity<Void> joinStudy(
            @Parameter(name = "studyId", in = ParameterIn.PATH, description = "스터디 id", required = true, example = "1")
            @PathVariable("studyId") @Valid @Positive @NotNull(message = "스터디 id는 필수 항목 입니다.") Long studyId
    ) {
        studyApiService.joinStudy(studyId);
        return ResponseDto.noContent();
    }

    @GetMapping("/tag")
    @Operation(summary = "스터디 생성 태그 키워드 조회")
    public ResponseEntity<TagResponse> getTagByKeyword(
            @RequestParam String keyword
    ) {
        var response = studyApiService.getTagByKeyword(keyword);
        return ResponseDto.ok(response);
    }

    @GetMapping
    @Operation(summary = "사용자 스터디 목록 조회")
    public ResponseEntity<StudiesInfoResponse> getUserAllStudiesByStatus(
            @RequestParam(defaultValue = "") List<StudyStatus> studyStatuses, // TODO : ?????????????????
            @RequestParam(defaultValue = "ACTIVE") StudyUserStatus joinStatus
    ) {
        var response = studyApiService.getUserAllStudiesByStatus(studyStatuses, joinStatus);
        return ResponseDto.ok(response);
    }

    @GetMapping("/{studyId}")
    @Operation(summary = "스터디 정보 단일 조회")
    public ResponseEntity<StudyDetailsResponse> getStudyDetails(
            @PathVariable final Long studyId
    ) {
        var response = studyApiService.getStudyDetails(studyId);
        return ResponseDto.ok(response);
    }

    @GetMapping("/{studyId}/enter")
    @Operation(summary = "참여 스터디 정보 조회")
    public ResponseEntity<StudyEntranceDetailsResponse> getStudyEntranceDetails(
            @PathVariable final Long studyId
    ) {
        var response = studyApiService.getStudyEntranceDetails(studyId);
        return ResponseDto.ok(response);
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "스터디 삭제")
    public ResponseEntity<Long> delete(
            @PathVariable final Long userId
    ) {
        var response = studyApiService.deleteAllByLeaderId(userId);
        return ResponseDto.ok(response);
    }

    @PostMapping("/{studyId}/leave")
    @Operation(summary = "스터디 탈퇴")
    public ResponseEntity<Void> leaveStudy(
            @PathVariable("studyId") @Valid @Positive @NotNull(message = "스터디 id는 필수 항목 입니다.") Long studyId
    ) {
        studyApiService.leaveStudy(studyId);
        return ResponseDto.noContent();
    }
}
