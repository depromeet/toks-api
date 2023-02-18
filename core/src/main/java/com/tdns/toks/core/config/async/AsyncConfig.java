package com.tdns.toks.core.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        var executor = new ExecutorGenerator(
                5,
                10,
                10,
                "taskExecutor"
        );

        return executor.generate();
    }

    @Bean(name = "systemActionLogExecutor")
    public ThreadPoolTaskExecutor systemActionLogExecutor() {
        var executor = new ExecutorGenerator(
                5,
                10,
                10,
                "systemActionLogExecutor"
        );

        return executor.generate();
    }

    @Bean(name = "tagDictionaryExecutor")
    public ThreadPoolTaskExecutor tagDictionaryExecutor() {
        var executor = new ExecutorGenerator(
                5,
                10,
                10,
                "tagDictionaryExecutor"
        );

        return executor.generate();
    }
}
