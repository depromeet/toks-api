package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizCustomRepository {
    Optional<Quiz> findFirstByStudyIdOrderByCreatedAtDesc(Long studyId);

    Boolean existsByRound(Integer round);

    Boolean existsByStudyIdAndRound(Long studyId, Integer round);
}
