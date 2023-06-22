package com.tdns.toks.core.domain.quizrank.repository;

import com.tdns.toks.core.domain.quizrank.model.entity.QuizRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRankRepository extends JpaRepository<QuizRank, Long> {
}
