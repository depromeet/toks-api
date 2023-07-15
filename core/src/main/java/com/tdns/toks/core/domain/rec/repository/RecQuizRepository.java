package com.tdns.toks.core.domain.rec.repository;

import com.tdns.toks.core.domain.rec.entity.RecQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RecQuizRepository extends JpaRepository<RecQuiz, Long> {
    @Transactional(readOnly = true)
    Optional<RecQuiz> findByRoundAndCategoryId(Integer round, String categoryId);
}
