package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.entity.QuizTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTagRepository extends JpaRepository<QuizTag, Long> {
}
