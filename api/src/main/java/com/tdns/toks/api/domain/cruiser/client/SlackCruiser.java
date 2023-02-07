package com.tdns.toks.api.domain.cruiser.client;

import com.tdns.toks.api.config.cruiser.CruiserProperties;
import com.tdns.toks.api.domain.cruiser.dto.CruiserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SlackCruiser implements CruiserClient {
    private final CruiserProperties cruiserProperties;
    private final WebClient.Builder webclient;

    @Override
    public void send(CruiserRequest request) {
        webclient.build()
                .post()
                .uri(cruiserProperties.getCruiser())
                .body(Mono.just(request), CruiserRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }
}
