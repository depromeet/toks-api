package com.tdns.toks.core.domain.quiz.repository;

import static com.tdns.toks.core.domain.quiz.model.entity.QQuizLike.*;
import static com.tdns.toks.core.domain.quiz.model.entity.QQuizReplyHistory.*;
import static com.tdns.toks.core.domain.user.model.entity.QUser.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tdns.toks.core.domain.quiz.model.dto.QuizReplyHistoryDto;
import com.tdns.toks.core.domain.quiz.model.entity.QuizReplyHistory;
import com.tdns.toks.core.domain.user.type.UserStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizReplyHistoryCustomRepositoryImpl implements QuizReplyHistoryCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<QuizReplyHistoryDto> retrieveByQuizId(final Long quizId, final Pageable pageable) {
		return jpaQueryFactory.select(
				Projections.fields(QuizReplyHistoryDto.class,
					quizReplyHistory.id.as("quizReplyHistoryId"),
					quizReplyHistory.answer.as("answer"),
					quizReplyHistory.count().as("likeNumber"),
					Projections.fields(QuizReplyHistoryDto.UserSimpleDTO.class,
						user.id.as("userId"),
						user.nickname.as("nickname"),
						user.profileImageUrl.as("profileImageUrl")
					).as("creator")
				)
			)
			.from(quizReplyHistory)
			.where(quizReplyHistory.quizId.eq(quizId)
				.and(user.status.eq(UserStatus.ACTIVE)))
			.innerJoin(user)
			.on(quizReplyHistory.createdBy.eq(user.id))
			.innerJoin(quizLike)
			.on(quizReplyHistory.id.eq(quizLike.quizReplyHistoryId))
			.orderBy(quizReplyHistory.count().desc())
			.orderBy(getOrderSpecifier(pageable.getSort()).toArray(OrderSpecifier[]::new))
			.groupBy(quizReplyHistory.id)
			.fetch();
	}

	private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
		List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
		sort.forEach(order -> {
				Order direction = order.isAscending() ? Order.ASC : Order.DESC;
				String prop = order.getProperty();

				PathBuilder orderByExpression = new PathBuilder(QuizReplyHistory.class, "quizReplyHistory");
				orderSpecifiers.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
			});
		return orderSpecifiers;
	}
}
