package com.tdns.toks.core.domain.quizcomment.repository;

import com.tdns.toks.core.domain.quizcomment.model.entity.QuizCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCommentLikeRepository extends JpaRepository<QuizCommentLike, Long> {
}
