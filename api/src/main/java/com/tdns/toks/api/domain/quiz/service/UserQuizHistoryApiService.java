package com.tdns.toks.api.domain.quiz.service;

import static com.tdns.toks.api.domain.quiz.model.dto.UserQuizHistoryApiDTO.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.quiz.model.mapper.UserQuizHistoryMapper;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.quiz.service.UserQuizHistoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserQuizHistoryApiService {
	private final UserQuizHistoryService userQuizHistoryService;
	private final QuizService quizService;
	private final UserQuizHistoryMapper mapper;

	public UserQuizHistoryResponse create(UserQuizHistoryRequest request) {
		quizService.getOrThrow(request.getQuizId());
		return UserQuizHistoryResponse.toResponse(userQuizHistoryService.save(mapper.toEntity(request)));
	}
}
