package com.tdns.toks.api.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO;
import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO.QuizLikeRequest;
import com.tdns.toks.api.domain.quiz.model.mapper.QuizLikeMapper;
import com.tdns.toks.core.domain.quiz.service.UserQuizHistoryService;
import com.tdns.toks.core.domain.quiz.service.QuizLikeService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizLikeApiService {
	private final QuizLikeService quizLikeService;
	private final UserQuizHistoryService userQuizHistoryService;
	private final QuizLikeMapper mapper;

	public QuizLikeApiDTO.QuizLikeResponse like(final QuizLikeRequest request) {
		var userQuizHistory = userQuizHistoryService.getOrThrow(request.getUserQuizHistoryId());
		quizLikeService.checkAlreadyLike(UserDetailDTO.get().getId(), userQuizHistory.getQuizId());

		return QuizLikeApiDTO.QuizLikeResponse.toResponse(quizLikeService.create(mapper.toEntity(request)));
	}
}
