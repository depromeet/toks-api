package com.tdns.toks.api.domain.quiz.service;

import static com.tdns.toks.api.domain.quiz.model.dto.UserQuizHistoryApiDTO.*;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.quiz.model.mapper.UserQuizHistoryMapper;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.quiz.service.UserQuizHistoryService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserQuizHistoryApiService {
	private final UserQuizHistoryService userQuizHistoryService;
	private final QuizService quizService;
	private final UserQuizHistoryMapper mapper;

	public UserQuizHistoryResponse submit(final UserQuizHistoryRequest request) {
		quizService.getOrThrow(request.getQuizId());
		userQuizHistoryService.checkAlreadySubmitted(request.getQuizId(), UserDetailDTO.get().getId());
		return UserQuizHistoryResponse.toResponse(userQuizHistoryService.save(mapper.toEntity(request)));
	}

	public UserQuizHistoriesResponse getAllByQuizId(final Long quizId, final Pageable pageable) {
		var quiz = quizService.getOrThrow(quizId);
		return new UserQuizHistoriesResponse(userQuizHistoryService.getAllByQuizId(quiz.getId(), pageable));
	}
}
