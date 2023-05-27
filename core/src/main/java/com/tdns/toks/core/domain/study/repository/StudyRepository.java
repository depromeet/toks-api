package com.tdns.toks.core.domain.study.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tdns.toks.core.domain.study.model.entity.Study;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long>, StudyCustomRepository {
	Long deleteAllByLeaderId(Long leaderId);

    @Transactional(readOnly = true)
	Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
