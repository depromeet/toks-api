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
					quiz.id.as("quizId"),
					quiz.question.as("question"),
					quiz.answer.as("answer"),
					quiz.quizType.as("quizType"),
					quiz.description.as("description"),
					quiz.startedAt.as("startedAt"),
					quiz.endedAt.as("endedAt"),
					quiz.imageUrls.as("imageUrls"),
					Projections.fields(UserSimpleDTO.class,
						user.id.as("userId"),
						user.nickname.as("nickname"),
						user.profileImageUrl.as("profileImageUrl")
					).as("creator")
				)
			)
			.from(quiz)
			.where(quiz.id.eq(id))
			.innerJoin(user)
			.on(quiz.createdBy.eq(user.id))
			.fetchOne());
	}
}
