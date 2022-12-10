package com.tdns.toks.core.domain.quizrank.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.domain.quizrank.model.dto.QuizRankDTO;
import com.tdns.toks.core.domain.quizrank.repository.QuizRankRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizRankService {

	private final QuizRankRepository quizRankRepository;

	public List<QuizRankDTO> getByStudyId(final Long studyId) {
		return quizRankRepository.retrieveByStudyId(studyId);
	}
}