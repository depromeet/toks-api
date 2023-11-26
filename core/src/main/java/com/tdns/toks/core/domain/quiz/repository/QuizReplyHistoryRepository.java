package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.entity.QuizReplyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface QuizReplyHistoryRepository extends JpaRepository<QuizReplyHistory, Long>, QuizReplyHistoryCustomRepository {
    @Transactional(readOnly = true)
    Boolean existsByQuizIdAndCreatedBy(Long quizId, Long createdBy);

    @Transactional(readOnly = true)
    Boolean existsByQuizIdAndUserUuid(Long quizId, String userUuid);

    @Transactional(readOnly = true)
    Long countByQuizIdAndAnswer(Long quizId, String answer);

    @Transactional(readOnly = true)
    Optional<QuizReplyHistory> findByQuizIdAndCreatedBy(Long quizId, Long createdBy);

    @Transactional(readOnly = true)
    Optional<QuizReplyHistory> findByQuizIdAndUserUuid(Long quizId, String userUuid);

    @Transactional(readOnly = true)
    Optional<QuizReplyHistory> findByQuizIdAndUserUuidAndCreatedBy(Long quizId, String userUuid, Long createdBy);

    @Transactional(readOnly = true)
    Long countByCreatedBy(long userId);

    @Transactional(readOnly = true)
    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
