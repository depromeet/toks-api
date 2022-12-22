package com.tdns.toks.core.domain.study.repository;

import static com.querydsl.core.group.GroupBy.*;
import static com.tdns.toks.core.domain.quiz.model.entity.QQuiz.*;
import static com.tdns.toks.core.domain.quiz.model.entity.QQuizReplyHistory.*;
import static com.tdns.toks.core.domain.study.model.entity.QStudyUser.*;
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
public class StudyUserCustomRepositoryImpl implements StudyUserCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<UserSimpleByQuizIdDTO> retrieveSubmittedByStudyId(Long studyId) {
		return new ArrayList<>(
			jpaQueryFactory
				.from(studyUser)
				.where(studyUser.studyId.eq(studyId)
					.and(user.status.eq(UserStatus.ACTIVE)))
				.innerJoin(user)
				.on(studyUser.userId.eq(user.id))
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
	public List<UserSimpleByQuizIdDTO> retrieveParticipantByStudyId(Long studyId) {
		return new ArrayList<>(
			jpaQueryFactory
				.from(studyUser)
				.where(studyUser.studyId.eq(studyId)
					.and(user.status.eq(UserStatus.ACTIVE))
					.and(quiz1.studyId.eq(studyId)))
				.innerJoin(user)
				.on(studyUser.userId.eq(user.id))
				.leftJoin(quiz1)
				.on(studyUser.studyId.eq(quiz1.studyId))
				.transform(groupBy(quiz1.id).as(
					Projections.fields(UserSimpleByQuizIdDTO.class,
						quiz1.id.as("quizId"),
						list(Projections.fields(UserSimpleDTO.class,
							user.id.as("userId"),
							user.nickname.as("nickname"),
							user.profileImageUrl.as("profileImageUrl")
						)).as("users")
					)
				)).values()
		);
	}
}
