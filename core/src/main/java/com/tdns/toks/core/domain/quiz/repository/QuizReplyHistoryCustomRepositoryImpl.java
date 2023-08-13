package com.tdns.toks.core.domain.quiz.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tdns.toks.core.domain.quiz.model.QuizReplyCountModel;
import lombok.RequiredArgsConstructor;

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
                .fetch();
    }
}
