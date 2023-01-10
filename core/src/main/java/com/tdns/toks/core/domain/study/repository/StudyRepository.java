package com.tdns.toks.core.domain.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tdns.toks.core.domain.study.model.entity.Study;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
	Long deleteAllByLeaderId(Long leaderId);
}
