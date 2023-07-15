package com.tdns.toks.core.domain.actionlog.repository;

import com.tdns.toks.core.domain.actionlog.entity.SystemActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface SystemActionLogRepository extends JpaRepository<SystemActionLog, Long> {
    @Transactional(readOnly = true)
    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAT);
}
