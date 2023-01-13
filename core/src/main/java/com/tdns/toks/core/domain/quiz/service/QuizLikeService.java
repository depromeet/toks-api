package com.tdns.toks.core.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.model.entity.QuizLike;
import com.tdns.toks.core.domain.quiz.repository.QuizLikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizLikeService {
	private final QuizLikeRepository quizLikeRepository;

	public QuizLike create(final QuizLike quizLike) {
		return quizLikeRepository.save(quizLike);
	}

	public void checkAlreadyLike(final Long userId, final Long quizId) {
		if (quizLikeRepository.countByUserIdAndQuizId(userId, quizId) == 1) {
			throw new SilentApplicationErrorException(ApplicationErrorType.ALREADY_LIKE_USER_QUIZ);
		}
	}

	public boolean isVoted(final Long userId, final Long quizId) {
		return quizLikeRepository.countByUserIdAndQuizId(userId, quizId) == 1;
	}
}
