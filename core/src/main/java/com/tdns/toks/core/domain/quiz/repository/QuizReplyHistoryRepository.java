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
    Boolean existsByQuizIdAndIpAddress(Long quizId, String ipAddress);

    @Transactional(readOnly = true)
    Long countByQuizIdAndAnswer(Long quizId, String answer);

    @Transactional(readOnly = true)
    Optional<QuizReplyHistory> findByQuizIdAndCreatedBy(Long quizId, Long createdBy);

    @Transactional(readOnly = true)
    Optional<QuizReplyHistory> findByQuizIdAndIpAddress(Long quizId, String ipAddress);

    @Transactional(readOnly = true)
    Optional<QuizReplyHistory> findByQuizIdAndIpAddressAndCreatedBy(Long quizId, String ipAddress, Long createdBy);

    @Transactional(readOnly = true)
    Long countByCreatedBy(long userId);

    @Transactional(readOnly = true)
    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
