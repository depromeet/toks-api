package com.tdns.toks.core.domain.ranking.repository;

import java.util.List;

import com.tdns.toks.core.domain.ranking.model.dto.RankingDto;

public interface CustomRankingRepository {

	List<RankingDto> retrieveByStudyId(final Long studyId);
}
