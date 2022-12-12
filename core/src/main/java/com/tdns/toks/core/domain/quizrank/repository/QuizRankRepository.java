package com.tdns.toks.core.domain.quizrank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.quizrank.model.entity.QuizRank;

public interface QuizRankRepository extends JpaRepository<QuizRank, Long>, CustomQuizRankRepository {
}
