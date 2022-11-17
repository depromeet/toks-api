package com.tdns.toks.api.domain.study.controller;

import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyApiResponse;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyCreateRequest;
import com.tdns.toks.api.domain.study.model.dto.StudyApiDTO.StudyFormResponse;
import com.tdns.toks.api.domain.study.service.StudyApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.common.type.JwtToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Tag(name = "StudyController-V1", description = "STUDY API")
@RestController
@RequestMapping(path = "/api/v1/study", produces = "application/json")
@RequiredArgsConstructor
public class StudyController {
    private final StudyApiService studyApiService;

    @PostMapping
    @Operation(
            method = "POST",
            summary = "스터디 생성"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtToken.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<StudyApiResponse> createStudy(
            StudyCreateRequest studyCreateRequest
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
        return ResponseDto.created();
    }
}
