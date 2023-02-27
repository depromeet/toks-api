package com.tdns.toks.core.domain.actionlog.repository;

import com.tdns.toks.core.domain.actionlog.model.SystemActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SystemActionLogRepository extends JpaRepository<SystemActionLog, Long> {
    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAT);
}
