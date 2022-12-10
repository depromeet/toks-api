package com.tdns.toks.api.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizResponse;
import com.tdns.toks.core.domain.quiz.service.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizApiService {
	private final QuizService quizService;

	public QuizResponse getById(final Long id) {
		return QuizResponse.toResponse(quizService.retrieveByIdOrThrow(id));
	}
}
