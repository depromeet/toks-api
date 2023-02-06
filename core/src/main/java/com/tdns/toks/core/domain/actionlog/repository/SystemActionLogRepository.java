package com.tdns.toks.core.domain.actionlog.repository;

import com.tdns.toks.core.domain.actionlog.model.SystemActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemActionLogRepository extends JpaRepository<SystemActionLog, Long> {
}
