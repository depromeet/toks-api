package com.tdns.toks.core.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.domain.quiz.model.entity.UserQuizHistory;
import com.tdns.toks.core.domain.quiz.repository.UserQuizHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserQuizHistoryService {
	private final UserQuizHistoryRepository userQuizHistoryRepository;

	public UserQuizHistory save(UserQuizHistory userQuizHistory) {
		return userQuizHistoryRepository.save(userQuizHistory);
	}
}
