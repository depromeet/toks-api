package com.tdns.toks.core.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.model.entity.UserQuizLike;
import com.tdns.toks.core.domain.quiz.repository.UserQuizLikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserQuizLikeService {
	private final UserQuizLikeRepository userQuizLikeRepository;

	public UserQuizLike create(Long userId, UserQuizLike userQuizLike) {
		checkLikeOrNot(userId, userQuizLike.getUserQuizHistoryId());

		return
	}

	private void checkLikeOrNot() {

	}
}
