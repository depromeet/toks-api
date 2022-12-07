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

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "UserQuizLikeController-V1", description = "USER QUIZ LIKE API")
@RestController
@RequestMapping(path = "/api/v1/user-quiz-like", produces = "application/json")
@RequiredArgsConstructor
public class UserQuizLikeController {
	private final UserQuizLikeApiService userQuizLikeApiService;

	@PostMapping
	public ResponseEntity<UserQuizLikeResponse> create(@Validated final UserQuizLikeRequest request) {
		var response = userQuizLikeApiService.create(request);
		return ResponseDto.ok(response);
	}
}
