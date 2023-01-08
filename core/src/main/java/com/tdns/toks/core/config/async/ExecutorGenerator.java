package com.tdns.toks.core.config.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@RequiredArgsConstructor
public class ExecutorGenerator {
    private final int corePoolSize;
    private final int maxPoolSize;
    private final int queueCapacity;
    private final String threadNamePrefix;

    public ThreadPoolTaskExecutor generate() {
        var threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(this.corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(this.maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(this.queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(this.threadNamePrefix + "-");
        threadPoolTaskExecutor.initialize();
        log.info("generate ThreadPoolTaskExecutor : " + this.threadNamePrefix);
        return threadPoolTaskExecutor;
    }
}
