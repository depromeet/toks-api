package com.tdns.toks.core.domain.ranking.repository;

import static com.tdns.toks.core.domain.ranking.model.entity.QRanking.*;
import static com.tdns.toks.core.domain.user.model.entity.QUser.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tdns.toks.core.domain.ranking.model.dto.RankingDto;
import com.tdns.toks.core.domain.user.type.UserStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomRankingRepositoryImpl implements CustomRankingRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<RankingDto> retrieveByStudyId(final Long studyId) {
		return jpaQueryFactory.select(
				Projections.fields(RankingDto.class,
					ranking.id.as("rankingId"),
					ranking.score.as("score"),
					Projections.fields(RankingDto.UserSimpleDTO.class,
						user.id.as("userId"),
						user.nickname.as("nickname"),
						user.profileImageUrl.as("profileImageUrl")
					).as("user")
				)
			)
			.from(ranking)
			.where(ranking.studyId.eq(studyId)
				.and(user.status.eq(UserStatus.ACTIVE)))
			.innerJoin(user)
			.on(ranking.userId.eq(user.id))
			.orderBy(ranking.score.desc())
			.fetch();
	}
}
