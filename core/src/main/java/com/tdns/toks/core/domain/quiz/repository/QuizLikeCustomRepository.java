package com.tdns.toks.core.domain.quiz.repository;

public interface QuizLikeCustomRepository {
	Long countByUserIdAndQuizId(final Long userId, final Long quizReplyHistoryId);
}
