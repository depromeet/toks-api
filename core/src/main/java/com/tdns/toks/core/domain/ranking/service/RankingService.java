package com.tdns.toks.core.domain.ranking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.domain.ranking.model.dto.RankingDto;
import com.tdns.toks.core.domain.ranking.repository.RankingRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RankingService {

	private final RankingRepository rankingRepository;

	public List<RankingDto> getByStudyId(final Long studyId) {
		return rankingRepository.retrieveByStudyId(studyId);
	}
}
