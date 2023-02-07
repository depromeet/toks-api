package com.tdns.toks.api.config.cruiser;

import com.tdns.toks.api.domain.cruiser.client.CruiserClient;
import com.tdns.toks.api.domain.cruiser.client.SlackCruiser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CruiserConfig {
    private final CruiserProperties cruiserProperties;
    private final WebClient.Builder webClient;

    @Bean
    public CruiserClient slackCruiser() {
        return new SlackCruiser(
                cruiserProperties,
                webClient
        );
    }
}
