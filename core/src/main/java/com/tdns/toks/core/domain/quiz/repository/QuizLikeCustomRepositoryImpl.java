package com.tdns.toks.core.domain.quiz.repository;

import static com.tdns.toks.core.domain.quiz.model.entity.QQuizLike.*;
import static com.tdns.toks.core.domain.quiz.model.entity.QQuizReplyHistory.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizLikeCustomRepositoryImpl implements QuizLikeCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Long countByUserIdAndQuizId(final Long userId, final Long quizReplyHistoryId) {
		return jpaQueryFactory.select(quizLike.id.count())
			.from(quizLike)
			.innerJoin(quizReplyHistory)
			.on(quizLike.quizReplyHistoryId.eq(quizReplyHistory.id))
			.where(quizLike.createdBy.eq(userId)
				.and(quizReplyHistory.quizId.eq(quizReplyHistoryId)))
			.fetchOne();
	}
}
