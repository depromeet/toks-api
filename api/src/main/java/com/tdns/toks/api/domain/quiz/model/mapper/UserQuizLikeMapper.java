package com.tdns.toks.api.domain.quiz.model.mapper;

import com.tdns.toks.api.domain.quiz.model.dto.UserQuizLikeApiDTO.UserQuizLikeRequest;
import com.tdns.toks.core.domain.quiz.model.entity.UserQuizLike;

public class UserQuizLikeMapper {

	public UserQuizLike toEntity(UserQuizLikeRequest request) {
		return UserQuizLike.from(request.getUserQuizHistoryId());
	}

}
