package com.tdns.toks.core.domain.quizrank.repository;

import static com.tdns.toks.core.domain.quizrank.model.entity.QQuizRank.*;
import static com.tdns.toks.core.domain.user.model.entity.QUser.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tdns.toks.core.domain.quizrank.model.dto.QuizRankDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;
import com.tdns.toks.core.domain.user.type.UserStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomQuizRankRepositoryImpl implements CustomQuizRankRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<QuizRankDTO> retrieveByStudyId(final Long studyId) {
		return jpaQueryFactory.select(
				Projections.fields(QuizRankDTO.class,
					quizRank.id.as("quizRankId"),
					quizRank.score.as("score"),
					Projections.fields(UserSimpleDTO.class,
						user.id.as("userId"),
						user.nickname.as("nickname"),
						user.profileImageUrl.as("profileImageUrl")
					).as("user")
				)
			)
			.from()
			.where(quizRank.studyId.eq(studyId)
				.and(user.status.eq(UserStatus.ACTIVE)))
			.innerJoin(user)
			.on(quizRank.userId.eq(user.id))
			.orderBy(quizRank.score.desc())
			.fetch();
	}
}
