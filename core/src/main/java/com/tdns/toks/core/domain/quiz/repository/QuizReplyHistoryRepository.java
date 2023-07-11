package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.model.entity.QuizReplyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface QuizReplyHistoryRepository extends JpaRepository<QuizReplyHistory, Long>, QuizReplyHistoryCustomRepository {
    @Transactional(readOnly = true)
    Boolean existsByQuizIdAndCreatedBy(Long quizId, Long createdBy);

    @Transactional(readOnly = true)
    Boolean existsByQuizIdAndIpAddress(Long quizId, String ipAddress);

    @Transactional(readOnly = true)
    Long countByQuizIdAndAnswer(Long quizId, String answer);
}
