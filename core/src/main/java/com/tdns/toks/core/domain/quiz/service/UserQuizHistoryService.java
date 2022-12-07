package com.tdns.toks.core.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.model.entity.UserQuizHistory;
import com.tdns.toks.core.domain.quiz.repository.UserQuizHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserQuizHistoryService {
	private final UserQuizHistoryRepository userQuizHistoryRepository;

	public UserQuizHistory save(final UserQuizHistory userQuizHistory) {
		return userQuizHistoryRepository.save(userQuizHistory);
	}

	public UserQuizHistory getOrThrow(final Long id) {
		return userQuizHistoryRepository.findById(id)
			.orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
	}

	public void checkAlreadySubmitted(final Long quizId, final Long userId) {
		if (userQuizHistoryRepository.existsByQuizIdAndCreatedBy(quizId, userId)) {
			throw new SilentApplicationErrorException(ApplicationErrorType.ALREADY_SUBMITTED_USER_QUIZ);
		}
	}
}
