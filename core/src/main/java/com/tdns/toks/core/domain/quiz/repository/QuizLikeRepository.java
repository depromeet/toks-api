package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.entity.QuizLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizLikeRepository extends JpaRepository<QuizLike, Long> {
}
