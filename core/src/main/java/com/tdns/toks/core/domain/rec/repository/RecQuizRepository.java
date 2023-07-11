package com.tdns.toks.core.domain.rec.repository;

import com.tdns.toks.core.domain.rec.model.RecQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecQuizRepository extends JpaRepository<RecQuiz, Long> {
    @Transactional(readOnly = true)
    List<RecQuiz> findAllByRoundAndCategoryId(Integer round, String categoryId);

    @Transactional(readOnly = true)
    Optional<RecQuiz> findByRoundAndCategoryId(Integer round, String categoryId);
}
