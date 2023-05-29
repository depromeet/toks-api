package com.tdns.toks.core.domain.quizcomment.repository;

import com.tdns.toks.core.domain.quizcomment.model.entity.QuizComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCommentRepository extends JpaRepository<QuizComment, Long> {

}
