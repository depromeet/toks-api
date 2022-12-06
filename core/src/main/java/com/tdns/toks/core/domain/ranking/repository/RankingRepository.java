package com.tdns.toks.core.domain.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdns.toks.core.domain.ranking.model.entity.Ranking;

public interface RankingRepository extends JpaRepository<Ranking, Long>, CustomRankingRepository {
}
