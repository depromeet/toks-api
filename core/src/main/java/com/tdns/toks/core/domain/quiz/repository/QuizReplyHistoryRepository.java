package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.entity.QuizReplyHistory;
import com.tdns.toks.core.domain.quiz.model.QuizReplyCountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QuizReplyHistoryRepository extends JpaRepository<QuizReplyHistory, Long> {
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
    @Query(value = "SELECT qrh.answer, count(qrh.id) FROM quiz_reply_history qrh WHERE qrh.quiz_id = :quizId AND qrh.answer IN :answer", nativeQuery = true)
    List<QuizReplyCountModel> findQuizReplyCount(@Param("quizId") Long quizId, @Param("answer") Set<String> answer);
}
