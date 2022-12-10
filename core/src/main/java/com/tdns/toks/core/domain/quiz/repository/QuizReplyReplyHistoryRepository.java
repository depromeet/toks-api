package com.tdns.toks.core.domain.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.quiz.model.entity.QuizReplyHistory;

public interface QuizReplyReplyHistoryRepository
	extends JpaRepository<QuizReplyHistory, Long>, QuizReplyHistoryCustomRepository {
	Boolean existsByQuizIdAndCreatedBy(Long quizId, Long createdBy);
}
