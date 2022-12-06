package com.tdns.toks.api.domain.quiz.model.mapper;

import com.tdns.toks.api.domain.quiz.model.dto.UserQuizHistoryApiDTO.UserQuizHistoryRequest;
import com.tdns.toks.core.domain.quiz.model.entity.UserQuizHistory;

public class UserQuizHistoryMapper {

	public UserQuizHistory toEntity(UserQuizHistoryRequest request) {
		return UserQuizHistory.of(request.getAnswer(), request.getQuizId());
	}

}
