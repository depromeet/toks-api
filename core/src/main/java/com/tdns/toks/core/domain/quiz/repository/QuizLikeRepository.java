package com.tdns.toks.core.domain.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.quiz.model.entity.QuizLike;

public interface QuizLikeRepository extends JpaRepository<QuizLike, Long>, QuizLikeCustomRepository {
}
