package com.tdns.toks.api.domain.quiz.model.mapper;

import org.springframework.stereotype.Component;

import com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;

@Component
public class QuizMapper {

	public Quiz toEntity(
		final QuizApiDTO.QuizRequest request
	) {
		return Quiz.of(
			request.getQuestion(),
			request.getQuizType(),
			request.getDescription(),
			request.getAnswer(),
			request.getStartedAt(),
			request.getStartedAt().plusSeconds(request.getDurationOfSecond()),
			request.getImageUrls(),
			request.getStudyId(),
			request.getRound()
		);
	}
}
