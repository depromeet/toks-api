package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.model.QuizReplyCountModel;
import com.tdns.toks.core.domain.quiz.model.UserDailySolveCountModel;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface QuizReplyHistoryCustomRepository {
    @Transactional(readOnly = true)
    List<QuizReplyCountModel> findQuizReplyCount(Long quizId, Set<String> answer);

    /**
     * 해당 월에 각 일별 푼 문제수 카운트
     */
    @Transactional(readOnly = true)
    List<UserDailySolveCountModel> findUserMonthlySolveActivity(Long userId, int month, int year);
    
    /**
     * 해당 일에 몇 문제 풀었는지 카운트
     */
    @Transactional(readOnly = true)
    Long countUserDailySolveActivity(Long userId, LocalDate date);
}
