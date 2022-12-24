package com.tdns.toks.core.domain.quiz.repository;

import static com.tdns.toks.core.domain.quiz.model.entity.QQuiz.*;
import static com.tdns.toks.core.domain.user.model.entity.QUser.*;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizCustomRepositoryImpl implements QuizCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<QuizSimpleDTO> retrieveById(Long id) {
		return Optional.ofNullable(jpaQueryFactory.select(
				Projections.fields(QuizSimpleDTO.class,
					quiz1.id.as("quizId"),
					quiz1.quiz.as("quiz"),
					quiz1.quizType.as("quizType"),
					quiz1.description.as("description"),
					quiz1.startedAt.as("startedAt"),
					quiz1.endedAt.as("endedAt"),
					quiz1.imageUrls.as("imageUrls"),
					quiz1.studyId.as("studyId"),
					Projections.fields(UserSimpleDTO.class,
						user.id.as("userId"),
						user.nickname.as("nickname"),
						user.profileImageUrl.as("profileImageUrl")
					).as("creator")
				)
			)
			.from(quiz1)
			.where(quiz1.id.eq(id))
			.innerJoin(user)
			.on(quiz1.createdBy.eq(user.id))
			.fetchOne());
	}

	@Override
	public List<QuizSimpleDTO> retrieveByStudyId(Long studyId) {
		return jpaQueryFactory.select(
				Projections.fields(QuizSimpleDTO.class,
					quiz1.id.as("quizId"),
					quiz1.quiz.as("quiz"),
					quiz1.quizType.as("quizType"),
					quiz1.description.as("description"),
					quiz1.startedAt.as("startedAt"),
					quiz1.endedAt.as("endedAt"),
					quiz1.imageUrls.as("imageUrls"),
					quiz1.studyId.as("studyId"),
					Projections.fields(UserSimpleDTO.class,
						user.id.as("userId"),
						user.nickname.as("nickname"),
						user.profileImageUrl.as("profileImageUrl")
					).as("creator")
				)
			)
			.from(quiz1)
			.where(quiz1.studyId.eq(studyId))
			.innerJoin(user)
			.on(quiz1.createdBy.eq(user.id))
			.orderBy(quiz1.createdAt.desc())
			.fetch();
	}
}
