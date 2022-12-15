package com.tdns.toks.core.domain.quiz.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quiz.type.QuizStatusType;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizService {
	private final QuizRepository quizRepository;

	public Quiz getOrThrow(final Long id) {
		return quizRepository.findById(id)
			.orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
	}

	public QuizSimpleDTO retrieveByIdOrThrow(final Long id) {
		return quizRepository.retrieveById(id)
			.orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
	}

	public List<QuizSimpleDTO> retrieveByStudyId(final Long studyId) {
		return quizRepository.retrieveByStudyId(studyId);
	}

	public QuizStatusType getQuizStatus(final LocalDateTime startedAt, final LocalDateTime endedAt) {
		LocalDateTime now = LocalDateTime.now();

		if (now.isBefore(startedAt)) {
			return QuizStatusType.TO_DO;
		} else if (now.isAfter(endedAt)) {
			return QuizStatusType.DONE;
		}
		return QuizStatusType.IN_PROGRESS;
	}
}
