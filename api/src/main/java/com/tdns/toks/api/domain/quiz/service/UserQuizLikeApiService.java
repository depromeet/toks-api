package com.tdns.toks.api.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.quiz.model.dto.UserQuizLikeApiDTO.UserQuizLikeRequest;
import com.tdns.toks.api.domain.quiz.model.dto.UserQuizLikeApiDTO.UserQuizLikeResponse;
import com.tdns.toks.api.domain.quiz.model.mapper.UserQuizLikeMapper;
import com.tdns.toks.core.domain.quiz.service.UserQuizHistoryService;
import com.tdns.toks.core.domain.quiz.service.UserQuizLikeService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserQuizLikeApiService {
	private final UserQuizLikeService userQuizLikeService;
	private final UserQuizHistoryService userQuizHistoryService;
	private final UserQuizLikeMapper mapper;

	public UserQuizLikeResponse like(final UserQuizLikeRequest request) {
		var userQuizHistory = userQuizHistoryService.getOrThrow(request.getUserQuizHistoryId());
		userQuizLikeService.checkAlreadyLike(UserDetailDTO.get().getId(), userQuizHistory.getQuizId());

		return UserQuizLikeResponse.toResponse(userQuizLikeService.create(mapper.toEntity(request)));
	}
}
