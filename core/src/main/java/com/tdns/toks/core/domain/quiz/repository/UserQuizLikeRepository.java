package com.tdns.toks.core.domain.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.quiz.model.entity.UserQuizLike;

public interface UserQuizLikeRepository extends JpaRepository<UserQuizLike, Long> {
	Boolean existsByCreatedBy(Long createdBy);
}
