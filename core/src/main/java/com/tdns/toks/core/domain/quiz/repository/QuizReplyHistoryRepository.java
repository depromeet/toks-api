package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.entity.QuizReplyHistory;
import com.tdns.toks.core.domain.user.model.UserDailySolveCountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    // 해당 월에 각 일별 푼 문제수 카운트
    @Transactional(readOnly = true)
    @Query(value = "select DATE_FORMAT(created_at,'%y-%m-%d') date, count(created_by) as value\n" +
            "from quiz_reply_history qrh\n" +
            "where qrh.created_by = :userId and DATE_FORMAT(created_at, '%m') = :month and DATE_FORMAT(created_at, '%Y') = :year\n" +
            "GROUP BY DATE\n" +
            "order by date", nativeQuery = true)
    List<UserDailySolveCountModel> findUserMonthlySolveActivity(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);

    // 해당 일에 몇 문제 풀었는지 카운트
    // date format : 20230802
    @Transactional(readOnly = true)
    @Query(value = "select count(qrh.created_by) as count\n" +
            "from quiz_reply_history qrh\n" +
            "where qrh.created_by = :userId and DATE_FORMAT(qrh.created_at,'%Y-%m-%d') = STR_TO_DATE(:date, '%Y%m%d')", nativeQuery = true)
    Long countUserDailySolveActivity(@Param("userId") Long userId, @Param("date") String date);

    @Transactional(readOnly = true)
    Long countByCreatedBy(long userId);

    @Transactional(readOnly = true)
    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
