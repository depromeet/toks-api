package com.tdns.toks.core.domain.quizcomment.repository;

import com.tdns.toks.core.domain.quizcomment.entity.QuizCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface QuizCommentLikeRepository extends JpaRepository<QuizCommentLike, Long> {
    @Transactional(readOnly = true)
    Boolean existsByCommentIdAndUid(Long commentId, Long uid);

    @Transactional(readOnly = true)
    Optional<QuizCommentLike> findByCommentIdAndUid(Long commentId, Long uid);
}
