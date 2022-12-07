package com.tdns.toks.core.domain.quiz.repository;

import static com.tdns.toks.core.domain.quiz.model.entity.QQuizLike.*;
import static com.tdns.toks.core.domain.quiz.model.entity.QUserQuizHistory.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizLikeCustomRepositoryImpl implements QuizLikeCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Long countByUserIdAndQuizId(final Long userId, final Long quizId) {
		return jpaQueryFactory.select(quizLike.id.count())
			.from(quizLike)
			.innerJoin(userQuizHistory)
			.on(quizLike.userQuizHistoryId.eq(userQuizHistory.id))
			.where(quizLike.createdBy.eq(userId)
				.and(userQuizHistory.quizId.eq(quizId)))
			.fetchOne();
	}
}
