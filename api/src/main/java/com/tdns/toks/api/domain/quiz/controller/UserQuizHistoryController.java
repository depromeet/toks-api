package com.tdns.toks.api.domain.quiz.controller;

import static com.tdns.toks.api.domain.quiz.model.dto.UserQuizHistoryApiDTO.*;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdns.toks.api.domain.quiz.service.UserQuizHistoryApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "UserQuizHistoryController-V1", description = "USER QUIZ HISTORY API")
@RestController
@RequestMapping(path = "/api/v1/user-quiz-histories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserQuizHistoryController {
	private final UserQuizHistoryApiService userQuizHistoryApiService;

	@PostMapping
	@Operation(
		method = "POST",
		summary = "퀴즈 답변 제출"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserQuizHistoryResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
		@ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
	public ResponseEntity<UserQuizHistoryResponse> submit(@Validated final UserQuizHistoryRequest request) {
		var response = userQuizHistoryApiService.submit(request);
		return ResponseDto.ok(response);
	}

	@GetMapping("/quizzes/{quizId}")
	@Operation(
		method = "Get",
		summary = "퀴즈 답변 다건 조회"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserQuizHistoryResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
		@ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
	public ResponseEntity<UserQuizHistoriesResponse> getAllByQuizId(
		@PathVariable final Long quizId,
		final Pageable pageable
	) {
		var response = userQuizHistoryApiService.getAllByQuizId(quizId, pageable);
		return ResponseDto.ok(response);
	}
}
