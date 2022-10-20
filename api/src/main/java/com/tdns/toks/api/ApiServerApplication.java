package com.tdns.toks.api;

import com.tdns.toks.BasePackageLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication(scanBasePackageClasses = BasePackageLocation.class)
@RequiredArgsConstructor
public class ApiServerApplication implements ApplicationListener<ApplicationReadyEvent> {
    private final Environment environment;

    public static void main(String[] args) {
        init();
        SpringApplication.run(ApiServerApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("applicationReady status" + Arrays.toString(environment.getActiveProfiles()));
    }

    private static void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
