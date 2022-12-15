package com.tdns.toks.api.domain.quizrank.service;

import static com.tdns.toks.api.domain.quizrank.model.dto.QuizRankApiDTO.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.domain.quizrank.service.QuizRankService;
import com.tdns.toks.core.domain.study.service.StudyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizRankApiService {

	private final StudyService studyService;
	private final QuizRankService quizRankService;

	public QuizRanksApiResponse getByStudyId(final Long studyId) {
		var study = studyService.getOrThrow(studyId);
		return new QuizRanksApiResponse(quizRankService.getByStudyId(study.getId()));
	}
}
