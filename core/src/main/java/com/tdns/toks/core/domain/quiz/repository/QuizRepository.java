package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Transactional(readOnly = true)
    Page<Quiz> findAllByCategoryIdIn(Set<String> categoryId, Pageable pageable);

    @Transactional(readOnly = true)
    List<Quiz> findTop3ByCategoryId(String categoryId);

    @Transactional(readOnly = true)
    Optional<Quiz> findQuizByIdAndDeleted(Long id, Boolean deleted);

    @Transactional(readOnly = true)
    Page<Quiz> findAllByDeletedOrderByCreatedAtDesc(Boolean deleted, Pageable pageable);

    @Transactional(readOnly = true)
    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
