package com.tdns.toks.core.domain.rank.repository;

import com.tdns.toks.core.domain.rank.model.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
}
