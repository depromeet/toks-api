package com.tdns.toks.api.domain.actionlog.event.listener;

import com.tdns.toks.api.domain.actionlog.event.model.SystemActionLogEvent;
import com.tdns.toks.core.domain.actionlog.entity.SystemActionLog;
import com.tdns.toks.core.domain.actionlog.repository.SystemActionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemActionLogEventListener {
    private final SystemActionLogRepository systemActionLogRepository;

    @Async(value = "systemActionLogExecutor")
    @EventListener(SystemActionLogEvent.class)
    public void subscribe(SystemActionLogEvent event) {
        /** health check는 action logging에서 제외시킴 */
        if (validateActionLog(event)) {
            return;
        }

        var systemActionLog = new SystemActionLog(
                event.getIpAddress(),
                event.getPath(),
                event.getMethod(),
                event.getUserAgent(),
                event.getHost(),
                event.getReferer()
        );

        systemActionLogRepository.save(systemActionLog);
    }

    private boolean validateActionLog(SystemActionLogEvent model) {
        return model.getPath().startsWith("/actuator/health") ||
                model.getIpAddress().startsWith("0:0:0:0:0:0:0:1") ||
                model.getIpAddress().startsWith("127.0.0.1");
    }
}
