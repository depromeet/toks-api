package com.tdns.toks.api.domain.quiz.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdns.toks.api.domain.quiz.service.QuizApiService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "QuizController-V1", description = "QUIZ API")
@RestController
@RequestMapping(path = "/api/v1/study", produces = "application/json")
@RequiredArgsConstructor
public class QuizController {

	private final QuizApiService quizApiService;

	@PostMapping
	public ResponseEntity<> create() {
		var object = quizApiService.create();

	}
}
