package com.tdns.toks.api.domain.quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdns.toks.api.domain.quiz.model.dto.UserQuizLikeApiDTO.UserQuizLikeRequest;
import com.tdns.toks.api.domain.quiz.model.dto.UserQuizLikeApiDTO.UserQuizLikeResponse;
import com.tdns.toks.api.domain.quiz.service.UserQuizLikeApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "UserQuizLikeController-V1", description = "USER QUIZ LIKE API")
@RestController
@RequestMapping(path = "/api/v1/user-quiz-like", produces = "application/json")
@RequiredArgsConstructor
public class UserQuizLikeController {
	private final UserQuizLikeApiService userQuizLikeApiService;

	@PostMapping
	@Operation(
		method = "POST",
		summary = "퀴즈 좋아요 생성"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserQuizLikeResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
		@ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
	public ResponseEntity<UserQuizLikeResponse> like(@Validated final UserQuizLikeRequest request) {
		var response = userQuizLikeApiService.like(request);
		return ResponseDto.ok(response);
	}
}
