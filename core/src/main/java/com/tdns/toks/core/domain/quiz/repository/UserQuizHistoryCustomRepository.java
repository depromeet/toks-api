package com.tdns.toks.core.domain.quiz.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tdns.toks.core.domain.quiz.model.dto.UserQuizHistoryDto;

public interface UserQuizHistoryCustomRepository {
	List<UserQuizHistoryDto> retrieveByQuizId(final Long quizId, final Pageable pageable);
}
