package com.tdns.toks.api.domain.quiz.controller;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdns.toks.api.domain.quiz.service.QuizApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "QuizController-V1", description = "QUIZ API")
@RestController
@RequestMapping(path = "/api/v1/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizController {
	private final QuizApiService quizApiService;

	@GetMapping("/{quizId}")
	@Operation(
		method = "GET",
		summary = "퀴즈 단건 조회"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = QuizSimpleResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
		@ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
	public ResponseEntity<QuizSimpleResponse> getById(@PathVariable final Long quizId) {
		var response = quizApiService.getById(quizId);
		return ResponseDto.ok(response);
	}

	@GetMapping("/studies/{studyId}")
	@Operation(
		method = "GET",
		summary = "퀴즈 다건 조회"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = QuizzesResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
		@ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
	public ResponseEntity<QuizzesResponse> getAllByStudyID(@PathVariable final Long studyId) {
		var response = quizApiService.getAllByStudyId(studyId);
		return ResponseDto.ok(response);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(
		method = "POST",
		summary = "퀴즈 생성"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = QuizCreateResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
		@ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
	public ResponseEntity<QuizCreateResponse> create(
		 @Validated @ModelAttribute QuizRequest request
	) {
		var response = quizApiService.create(request);
		return ResponseDto.ok(response);
	}
}
