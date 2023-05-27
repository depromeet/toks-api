package com.tdns.toks.core.domain.quizrank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.quizrank.model.entity.QuizRank;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuizRankRepository extends JpaRepository<QuizRank, Long>, CustomQuizRankRepository {
    @Transactional(readOnly = true)
    Optional<QuizRank> findByUserIdAndStudyId(Long userId, Long StudyId);
}
