package com.tdns.toks.core.domain.quiz.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tdns.toks.core.domain.quiz.model.QuizReplyCountModel;
import com.tdns.toks.core.domain.quiz.model.UserDailySolveCountModel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static com.tdns.toks.core.domain.quiz.entity.QQuizReplyHistory.quizReplyHistory;

@RequiredArgsConstructor
public class QuizReplyHistoryCustomRepositoryImpl implements QuizReplyHistoryCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<QuizReplyCountModel> findQuizReplyCount(Long quizId, Set<String> answer) {
        return jpaQueryFactory.select(
                        Projections.fields(QuizReplyCountModel.class,
                                quizReplyHistory.answer.as("answer"),
                                quizReplyHistory.quizId.count().as("count")
                        )
                ).from(quizReplyHistory)
                .where(quizReplyHistory.quizId.eq(quizId).and(quizReplyHistory.answer.in(answer)))
                .groupBy(quizReplyHistory.answer)
                .fetch();
    }

    @Override
    public List<UserDailySolveCountModel> findUserMonthlySolveActivity(Long userId, int month, int year) {
        var formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , quizReplyHistory.createdAt
                , ConstantImpl.create("%Y-%m-%d"));


        return jpaQueryFactory.select(
                        Projections.fields(UserDailySolveCountModel.class,
                                formattedDate.as("date"),
                                quizReplyHistory.createdBy.count().as("value")
                        )
                ).from(quizReplyHistory)
                .where(
                        quizReplyHistory.createdBy.eq(userId),
                        quizReplyHistory.createdAt.month().eq(month),
                        quizReplyHistory.createdAt.year().eq(year)
                )
                .groupBy(formattedDate)
                .orderBy(formattedDate.asc())
                .fetch();
    }

    @Override
    public Long countUserDailySolveActivity(Long userId, LocalDate date) {
        var startOfDay = LocalDateTime.of(date, LocalTime.MIN);
        var endOfDay = LocalDateTime.of(date, LocalTime.MAX);
        
        return jpaQueryFactory.select(
                        quizReplyHistory.createdBy.count().as("count")
                ).from(quizReplyHistory)
                .where(
                        quizReplyHistory.createdBy.eq(userId),
                        quizReplyHistory.createdAt.year().eq(date.getYear()),
                        quizReplyHistory.createdAt.month().eq(date.getMonthValue()),
                        quizReplyHistory.createdAt.between(startOfDay, endOfDay)
                )
                .fetchFirst();
    }
}
