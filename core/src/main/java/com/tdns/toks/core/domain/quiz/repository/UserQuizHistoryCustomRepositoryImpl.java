package com.tdns.toks.core.domain.quiz.repository;

import static com.tdns.toks.core.domain.quiz.model.entity.QQuizLike.*;
import static com.tdns.toks.core.domain.quiz.model.entity.QUserQuizHistory.*;
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
import com.tdns.toks.core.domain.quiz.model.dto.UserQuizHistoryDto;
import com.tdns.toks.core.domain.quiz.model.entity.UserQuizHistory;
import com.tdns.toks.core.domain.user.type.UserStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQuizHistoryCustomRepositoryImpl implements UserQuizHistoryCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<UserQuizHistoryDto> retrieveByQuizId(final Long quizId, final Pageable pageable) {
		return jpaQueryFactory.select(
				Projections.fields(UserQuizHistoryDto.class,
					userQuizHistory.id.as("userQuizHistoryId"),
					userQuizHistory.answer.as("answer"),
					userQuizHistory.count().as("likeNumber"),
					Projections.fields(UserQuizHistoryDto.UserSimpleDTO.class,
						user.id.as("userId"),
						user.nickname.as("nickname"),
						user.profileImageUrl.as("profileImageUrl")
					).as("creator")
				)
			)
			.from(userQuizHistory)
			.where(userQuizHistory.quizId.eq(quizId)
				.and(user.status.eq(UserStatus.ACTIVE)))
			.innerJoin(user)
			.on(userQuizHistory.createdBy.eq(user.id))
			.innerJoin(quizLike)
			.on(userQuizHistory.id.eq(quizLike.userQuizHistoryId))
			.orderBy(getOrderSpecifier(pageable.getSort()).toArray(OrderSpecifier[]::new))
			.groupBy(userQuizHistory.id)
			.fetch();
	}

	private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
		List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
		sort.forEach(order -> {
				Order direction = order.isAscending() ? Order.ASC : Order.DESC;
				String prop = order.getProperty();

				if (prop.equals("likeNumber")) {
					orderSpecifiers.add(getNumberLikeOrderSpecifier(direction));
					return;
				}

				PathBuilder orderByExpression = new PathBuilder(UserQuizHistory.class, "userQuizHistory");
				orderSpecifiers.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
			});
		return orderSpecifiers;
	}

	private OrderSpecifier getNumberLikeOrderSpecifier(Order order) {
		if (order == Order.ASC) {
			return userQuizHistory.count().asc();
		}
		return userQuizHistory.count().desc();
	}
}
