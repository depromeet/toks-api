package com.tdns.toks.core.domain.quiz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;

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
}
