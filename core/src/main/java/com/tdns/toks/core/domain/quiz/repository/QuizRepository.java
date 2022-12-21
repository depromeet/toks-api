package com.tdns.toks.core.domain.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.quiz.model.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizCustomRepository {
}
