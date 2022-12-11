package com.tdns.toks.core.domain.user.repository;

import static com.querydsl.core.group.GroupBy.*;
import static com.tdns.toks.core.domain.quiz.model.entity.QQuizReplyHistory.*;
import static com.tdns.toks.core.domain.quizrank.model.entity.QQuizRank.*;
import static com.tdns.toks.core.domain.user.model.entity.QUser.*;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleByQuizIdDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;
import com.tdns.toks.core.domain.user.type.UserStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<UserSimpleByQuizIdDTO> retrieveSubmittedByStudyId(Long studyId) {
		return new ArrayList<>(
			jpaQueryFactory
				.from(user)
				.where(quizRank.studyId.eq(studyId)
					.and(user.status.eq(UserStatus.ACTIVE)))
				.innerJoin(quizRank)
				.on(quizRank.userId.eq(user.id))
				.innerJoin(quizReplyHistory)
				.on(quizReplyHistory.createdBy.eq(user.id))
				.transform(groupBy(quizReplyHistory.quizId).as(
					Projections.fields(UserSimpleByQuizIdDTO.class,
						quizReplyHistory.quizId.as("quizId"),
						list(Projections.fields(UserSimpleDTO.class,
							user.id.as("userId"),
							user.nickname.as("nickname"),
							user.profileImageUrl.as("profileImageUrl")
						)).as("users")
					)
				)).values()
		);
	}

	@Override
	public List<UserSimpleDTO> retrieveParticipantByStudyId(Long studyId) {
		return jpaQueryFactory
			.select(
				Projections.fields(UserSimpleDTO.class,
					user.id.as("userId"),
					user.nickname.as("nickname"),
					user.profileImageUrl.as("profileImageUrl")))
			.from(user)
			.where(quizRank.studyId.eq(studyId)
				.and(user.status.eq(UserStatus.ACTIVE)))
			.innerJoin(quizRank)
			.on(quizRank.userId.eq(user.id))
			.fetch();
	}
}
