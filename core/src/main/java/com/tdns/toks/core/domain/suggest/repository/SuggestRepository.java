package com.tdns.toks.core.domain.suggest.repository;

import com.tdns.toks.core.domain.suggest.entity.Suggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestRepository extends JpaRepository<Suggest, Long> {
}
