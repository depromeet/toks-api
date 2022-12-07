package com.tdns.toks.api.domain.ranking.service;

import static com.tdns.toks.api.domain.ranking.model.dto.RankingApiDTO.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.domain.ranking.service.RankingService;
import com.tdns.toks.core.domain.study.service.StudyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RankingApiService {

	private final StudyService studyService;
	private final RankingService rankingService;

	public RankingsApiResponse getByStudyId(final Long studyId) {
		var study = studyService.getOrThrow(studyId);
		return new RankingsApiResponse(rankingService.getByStudyId(study.getId()));
	}
}
