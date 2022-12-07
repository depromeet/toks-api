package com.tdns.toks.api.domain.quiz.model.mapper;

import org.springframework.stereotype.Component;

import com.tdns.toks.api.domain.quiz.model.dto.UserQuizLikeApiDTO.UserQuizLikeRequest;
import com.tdns.toks.core.domain.quiz.model.entity.UserQuizLike;

@Component
public class UserQuizLikeMapper {

	public UserQuizLike toEntity(UserQuizLikeRequest request) {
		return UserQuizLike.from(request.getUserQuizHistoryId());
	}

}
