package com.tdns.toks.core.domain.quizcomment.repository;

import com.tdns.toks.core.domain.quizcomment.entity.QuizComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface QuizCommentRepository extends JpaRepository<QuizComment, Long> {
    @Transactional(readOnly = true)
    Page<QuizComment> findAllByQuizId(Long quizId, Pageable pageable);

    @Transactional(readOnly = true)
    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
