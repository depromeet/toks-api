package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.model.QuizReplyCountModel;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface QuizReplyHistoryCustomRepository {
    @Transactional(readOnly = true)
    List<QuizReplyCountModel> findQuizReplyCount(
            @Param("quizId") Long quizId,
            @Param("answer") Set<String> answer
    );
}
