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

	public UserQuizLike create(final UserQuizLike userQuizLike) {
		return userQuizLikeRepository.save(userQuizLike);
	}

	public void checkAlreadyLike(final Long userId, final Long quizId) {
		if (userQuizLikeRepository.countByUserIdAndQuizId(userId, quizId) == 1) {
			throw new SilentApplicationErrorException(ApplicationErrorType.ALREADY_LIKE_USER_QUIZ);
		}
	}
}
