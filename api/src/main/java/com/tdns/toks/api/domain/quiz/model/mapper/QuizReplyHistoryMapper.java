package com.tdns.toks.api.domain.quiz.model.mapper;

import org.springframework.stereotype.Component;

import com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoryRequest;
import com.tdns.toks.core.domain.quiz.model.entity.QuizReplyHistory;

@Component
public class QuizReplyHistoryMapper {

	public QuizReplyHistory toEntity(QuizReplyHistoryRequest request) {
		return QuizReplyHistory.of(request.getAnswer(), request.getQuizId());
	}

}
