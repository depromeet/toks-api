package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizCustomRepository {
    @Transactional(readOnly = true)
    Optional<Quiz> findFirstByStudyIdOrderByCreatedAtDesc(Long studyId);

    @Transactional(readOnly = true)
    Boolean existsByRound(Integer round);

    @Transactional(readOnly = true)
    Boolean existsByStudyIdAndRound(Long studyId, Integer round);

    @Transactional(readOnly = true)
    Page<Quiz> findAllByCategoryIdIn(List<String> categoryId, Pageable pageable);
}
