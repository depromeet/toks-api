package com.tdns.toks.api.domain.quiz.controller;

import static com.tdns.toks.api.domain.quiz.model.dto.UserQuizHistoryApiDTO.*;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdns.toks.api.domain.quiz.service.UserQuizHistoryApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "UserQuizHistoryController-V1", description = "USER QUIZ HISTORY API")
@RestController
@RequestMapping(path = "/api/v1/study", produces = "application/json")
@RequiredArgsConstructor
public class UserQuizHistoryController {
	private final UserQuizHistoryApiService userQuizHistoryApiService;

	@PostMapping
	public ResponseEntity<UserQuizHistoryResponse> create(@Validated final UserQuizHistoryRequest request) {
		var response = userQuizHistoryApiService.create(request);
		return ResponseDto.ok(response);
	}
}
