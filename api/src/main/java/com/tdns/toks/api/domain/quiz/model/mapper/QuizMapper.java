package com.tdns.toks.api.domain.quiz.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;

@Component
public class QuizMapper {

	public Quiz toEntity(
		final QuizApiDTO.QuizRequest request,
		final List<String> urls
	) {
		return Quiz.of(
			request.getQuiz(),
			request.getQuizType(),
			request.getDescription(),
			request.getAnswer(),
			request.getStartedAt(),
			request.getEndedAt(),
			urls,
			request.getStudyId()
		);
	}
}
