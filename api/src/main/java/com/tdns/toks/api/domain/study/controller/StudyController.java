package com.tdns.toks.api.domain.study.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// TODO : 해당 클래스에 있는 조회 로직에 대해 개선 작업이 필요함
@Tag(name = "StudyController-V1", description = "STUDY API")
@RestController
@RequestMapping(path = "/api/v1/studies", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudyController {
    private final StudyApiService studyApiService;

    @PostMapping
    @Operation(
            method = "POST",
            summary = "스터디 생성"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StudyApiResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<StudyApiResponse> createStudy(
            @Validated @RequestBody StudyCreateRequest studyCreateRequest
    ) {
        var response = studyApiService.createStudy(studyCreateRequest);
        return ResponseDto.created(response);
    }

    @GetMapping("/form-data")
    @Operation(
            method = "GET",
            summary = "스터디 생성용 폼 데이터 조회"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StudyFormResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<StudyFormResponse> getFormData(
    ) {
        var response = studyApiService.getFormData();
        return ResponseDto.ok(response);
    }

    @PostMapping("/{studyId}/join")
    @Operation(
            method = "POST",
            summary = "스터디 가입"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<Void> joinStudy(
            @Parameter(name = "studyId", in = ParameterIn.PATH, description = "스터디 id", required = true, example = "1")
            @PathVariable("studyId") @Valid @Positive @NotNull(message = "스터디 id는 필수 항목 입니다.") Long studyId
    ) {
        studyApiService.joinStudy(studyId);
        return ResponseDto.noContent();
    }

    @GetMapping("/tag")
    @Operation(
            method = "GET",
            summary = "스터디 생성 태그 키워드 조회"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TagResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<TagResponse> getTagByKeyword(
            @RequestParam String keyword
    ) {
        var response = studyApiService.getTagByKeyword(keyword);
        return ResponseDto.ok(response);
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "사용자 스터디 목록 조회"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StudiesInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<StudiesInfoResponse> getUserAllStudiesByStatus(@RequestParam(defaultValue = "") List<StudyStatus> statuses) {
        var response = studyApiService.getUserAllStudiesByStatus(statuses);
        return ResponseDto.ok(response);
    }

    @GetMapping("/{studyId}")
    @Operation(
            method = "GET",
            summary = "스터디 정보 단일 조회"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StudyDetailsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<StudyDetailsResponse> getStudyDetails(
            @PathVariable final Long studyId
    ) {
        var response = studyApiService.getStudyDetails(studyId);
        return ResponseDto.ok(response);
    }

    @GetMapping("/{studyId}/enter")
    @Operation(
            method = "GET",
            summary = "참여 스터디 정보 조회"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StudyEntranceDetailsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<StudyEntranceDetailsResponse> getStudyEntranceDetails(
            @PathVariable final Long studyId
    ) {
        var response = studyApiService.getStudyEntranceDetails(studyId);
        return ResponseDto.ok(response);
    }

    @DeleteMapping("/users/{userId}")
    @Operation(
        method = "DELETE",
        summary = "스터디 삭제"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StudyEntranceDetailsResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
        @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<Long> delete(
        @PathVariable final Long userId
    ) {
        var response = studyApiService.deleteAllByLeaderId(userId);
        return ResponseDto.ok(response);
    }
}
