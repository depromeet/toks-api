package com.tdns.toks.core.domain.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.quiz.model.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizCustomRepository {
    Optional<Quiz> findFirstByStudyIdOrderByCreatedAtDesc(Long studyId);

    Boolean existsByRound(Integer round);
}
