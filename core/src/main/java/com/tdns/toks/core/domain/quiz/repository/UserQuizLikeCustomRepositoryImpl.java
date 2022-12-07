package com.tdns.toks.core.domain.quiz.repository;

import static com.tdns.toks.core.domain.quiz.model.entity.QUserQuizHistory.*;
import static com.tdns.toks.core.domain.quiz.model.entity.QUserQuizLike.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQuizLikeCustomRepositoryImpl implements UserQuizLikeCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Long countByUserIdAndQuizId(final Long userId, final Long quizId) {
		return jpaQueryFactory.select(userQuizLike.id.count())
			.from(userQuizLike)
			.innerJoin(userQuizHistory)
			.on(userQuizLike.userQuizHistoryId.eq(userQuizHistory.id))
			.where(userQuizLike.createdBy.eq(userId)
				.and(userQuizHistory.quizId.eq(quizId)))
			.fetchOne();
	}
}
