package com.tdns.toks.api.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizSimpleResponse;
import com.tdns.toks.core.domain.quiz.service.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizApiService {
	private final QuizService quizService;

	public QuizSimpleResponse getById(final Long id) {
		return QuizSimpleResponse.toResponse(quizService.retrieveByIdOrThrow(id));
	}
}
