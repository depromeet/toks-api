package com.tdns.toks.api.config.cruiser;

import com.tdns.toks.api.domain.cruiser.client.CruiserClient;
import com.tdns.toks.api.domain.cruiser.client.SlackCruiser;
import com.tdns.toks.core.common.client.WebClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CruiserConfig {
    private final CruiserProperties cruiserProperties;

    @Bean
    public CruiserClient slackCruiser() {
        var webClient = WebClientFactory.generate(
                cruiserProperties.getCruiser(),
                10000,
                10000L,
                10000L
        );

        return new SlackCruiser(webClient);
    }
}
