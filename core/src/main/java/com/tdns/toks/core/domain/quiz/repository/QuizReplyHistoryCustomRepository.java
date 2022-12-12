package com.tdns.toks.core.domain.quiz.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tdns.toks.core.domain.quiz.model.dto.QuizReplyHistoryDto;

public interface QuizReplyHistoryCustomRepository {
	List<QuizReplyHistoryDto> retrieveByQuizId(final Long quizId, final Pageable pageable);
}
