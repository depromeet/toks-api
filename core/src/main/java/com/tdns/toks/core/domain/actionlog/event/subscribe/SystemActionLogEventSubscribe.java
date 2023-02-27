package com.tdns.toks.core.domain.actionlog.event.subscribe;

import com.tdns.toks.core.domain.actionlog.event.model.SystemActionLogEventModel;
import com.tdns.toks.core.domain.actionlog.model.SystemActionLog;
import com.tdns.toks.core.domain.actionlog.service.SystemActionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemActionLogEventSubscribe {
    private final SystemActionLogService systemActionLogService;

    @Async(value = "systemActionLogExecutor")
    @EventListener(SystemActionLogEventModel.class)
    public void subscribe(SystemActionLogEventModel model) {
        /** health check는 action logging에서 제외시킴 */
        if (model.getPath().startsWith("/actuator/health")) {
            return;
        }

        var systemActionLog = new SystemActionLog(
                model.getIpAddress(),
                model.getPath(),
                model.getMethod(),
                model.getUserAgent(),
                model.getHost(),
                model.getReferer()
        );

        systemActionLogService.insert(systemActionLog);
    }
}
