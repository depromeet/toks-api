package com.tdns.toks.core.domain.actionlog.service;

import com.tdns.toks.core.domain.actionlog.model.SystemActionLog;
import com.tdns.toks.core.domain.actionlog.repository.SystemActionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SystemActionLogService {
    private final SystemActionLogRepository systemActionLogRepository;

    public void insert(SystemActionLog systemActionLog) {
        systemActionLogRepository.save(systemActionLog);
    }

    public Long countApiCallCount() {
        var endAt = LocalDateTime.now();
        var startAt = endAt.minusDays(1);
        return systemActionLogRepository.countByCreatedAtBetween(startAt, endAt);
    }
}
