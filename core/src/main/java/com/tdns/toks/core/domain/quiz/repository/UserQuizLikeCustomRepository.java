package com.tdns.toks.core.domain.quiz.repository;

public interface UserQuizLikeCustomRepository {
	Long countByUserIdAndQuizId(final Long userId, final Long userQuizHistoryId);
}
