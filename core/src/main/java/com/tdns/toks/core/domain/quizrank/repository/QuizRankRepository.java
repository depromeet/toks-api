package com.tdns.toks.core.domain.quizrank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.quizrank.model.entity.QuizRank;

public interface QuizRankRepository extends JpaRepository<QuizRank, Long>, CustomQuizRankRepository {
	Optional<QuizRank> findByUserIdAndStudyId(Long userId, Long StudyId);
}
