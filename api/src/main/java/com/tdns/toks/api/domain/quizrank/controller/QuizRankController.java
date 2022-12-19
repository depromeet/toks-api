package com.tdns.toks.api.domain.quizrank.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdns.toks.api.domain.quizrank.model.dto.QuizRankApiDTO;
import com.tdns.toks.api.domain.quizrank.service.QuizRankApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "QuizRankController-V1", description = " API")
@RestController
@RequestMapping(path = "/api/v1/quiz-ranks", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizRankController {
	private final QuizRankApiService quizRankApiService;

	@GetMapping("studies/{studyId}")
	@Operation(
		method = "GET",
		summary = "순위 조회"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = QuizRankApiDTO.QuizRanksApiResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
		@ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
	public ResponseEntity<QuizRankApiDTO.QuizRanksApiResponse> getByStudyId(@PathVariable final Long studyId) {
		var response = quizRankApiService.getByStudyId(studyId);
		return ResponseDto.ok(response);
	}
}
