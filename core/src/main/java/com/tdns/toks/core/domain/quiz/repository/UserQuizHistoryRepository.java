package com.tdns.toks.core.domain.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.quiz.model.entity.UserQuizHistory;

public interface UserQuizHistoryRepository extends JpaRepository<UserQuizHistory, Long> {
	Boolean existsByQuizIdAndCreatedBy(Long quizId, Long createdBy);
}
