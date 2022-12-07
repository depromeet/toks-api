package com.tdns.toks.api.domain.quiz.model.mapper;

import org.springframework.stereotype.Component;

import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO.QuizLikeRequest;
import com.tdns.toks.core.domain.quiz.model.entity.QuizLike;

@Component
public class QuizLikeMapper {

	public QuizLike toEntity(QuizLikeRequest request) {
		return QuizLike.from(request.getUserQuizHistoryId());
	}

}
