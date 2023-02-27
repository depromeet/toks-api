package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
    Long deleteAllByLeaderId(Long leaderId);

    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
