package com.tdns.toks.api.domain.quiz.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO.QuizLikeRequest;
import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO.QuizLikeResponse;
import com.tdns.toks.api.domain.quiz.service.QuizLikeApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "QuizLikeController-V1", description = "QUIZ LIKE API")
@RestController
@RequestMapping(path = "/api/v1/quiz-likes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizLikeController {
	private final QuizLikeApiService quizLikeApiService;

	@PostMapping
	@Operation(
		method = "POST",
		summary = "퀴즈 답변 좋아요"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = QuizLikeResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
		@ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
	public ResponseEntity<QuizLikeResponse> like(@Validated @RequestBody final QuizLikeRequest request) {
		var response = quizLikeApiService.like(request);
		return ResponseDto.ok(response);
	}
}
